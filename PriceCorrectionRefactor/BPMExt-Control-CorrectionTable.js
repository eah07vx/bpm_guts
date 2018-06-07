bpmext_control_InitCorrectionTable = function(domClass)
{
 	this._instance = {
		offset: 0,
	};

	if (!this.constructor.prototype._proto)
	{
		this.constructor.prototype._proto =
		{
			//>> Performance hacks /////////////////////////////////////////////////////////////////////
			fixTable: function(view, table, globalSelCallback)
			{
				//Safeguard to prevent fixing Table's prototype multiple times
				if(table.constructor.prototype.appendRecords2)
					return;
				
				//Table workaround to prevent constant reloading of rows when records are added
				table.constructor.prototype.refreshStats = function()
				{
					this._proto._refreshTableStats(this);
					this._proto._refreshPaginationStatus(this);					
				}
				
				//Added appendRecords2 method - allows appending multiple records without causing
				//a reload every time, and uses flag to trigger the table to refresh its internal
				//paging state at the end
				table.constructor.prototype.appendRecords2 = function(records, finalize)
				{
					var fnRef = this.constructor.prototype._proto._reloadList;
					var view = this;
					
					try
					{
						//Temporarily replacing reload w/ NOOP 
						this.constructor.prototype._proto._reloadList = function(){};
						//Appending the records to the table (now computationally cheaper)
						this.appendRecords(records);
					}
					finally
					{
						//Restoring original function reference
						setTimeout(function(){view.constructor.prototype._proto._reloadList = fnRef});
					}
					
					if(finalize)
					{
						setTimeout(function(){
							view.refreshStats();					
						});
					}
				}
				
				//Table has no event handler for global selection (that's a huge oversight)
				//This provides a hook to handle global selection changes
				if(globalSelCallback != null)
				{
					var globalSelCbx = table.context.element.querySelector(".SelCtl input");
					
					globalSelCbx.addEventListener("change", globalSelCallback);
				}
			},
			
			//<< Performance hacks /////////////////////////////////////////////////////////////////////
			//>> Utilities /////////////////////////////////////////////////////////////////////////////
			/*
				Get the first child Coach view directly under a parent Coach View
			*/
			getChildView: function(view, parentCV)
			{
				for(var subviewId in parentCV.context.subview)
				{
					var childCV = parentCV.context.getSubview(subviewId);
					
					if(childCV == null)
						break;
					
					if(childCV.length == 0)
						break;
					
					return childCV[0];
				}
			},
			/*
				Gets all child Coach view directly under a parent Coach View
			*/
			getChildViews: function(view, parentCV)
			{
				var res = [];
				
				for(var subviewId in parentCV.context.subview)
				{
					var childCV = parentCV.context.getSubview(subviewId);
					
					if(childCV == null)
						break;
					
					for(var i = 0; i < childCV.length; i++)
					{
						res.push(childCV[i]);
					}
				}
				
				return res;
			},
			formatDate: function(date, forceUTC) {
	
				var month = (date.getMonth() + 1) > 9 ? String(date.getMonth() + 1) : "0" + String(date.getMonth() + 1);
				var day = date.getDate() > 9 ? String(date.getDate()) : "0" + String(date.getDate());
				var year = String(date.getFullYear());
				var fullDateStr = (forceUTC ? year + '-' + month + '-' + day + 'T00:00:00.000Z' : year + month + day);
				return fullDateStr;
			},
			searchTable: function(view){ 
				var searchItems = view.ui.get("Criteria").getData().items;
				var includeAdbill = view.ui.get("IncludeAdbill").isChecked();
				var includeNetChange = view.ui.get("IncludeNetChange").isChecked();
				var showLockedRows = view.ui.get("ShowLockedRows").isChecked();
				var isPOCorrection = view.context.options.crrbRequest.get("value").get("caseType");
				
				if (isPOCorrection == "PO") {
					view.ui.get("IncludeNetChange").setChecked(true);
					var includeNetChange = view.ui.get("IncludeNetChange").isChecked();
					view.ui.get("IncludeNetChange").setEnabled(false);
				}
				else {
					var includeNetChange = view.ui.get("IncludeNetChange").isChecked();		
				}

				view.ui.get("Table").search(function(listItem){
				
					var rebillAmt = listItem.rebillQty * listItem.newPrice;
					var creditAmt = listItem.rebillQty * listItem.oldPrice;

					if(!includeAdbill){
						if(rebillAmt>creditAmt){
							return false;
						}
					}
					
					if(!includeNetChange){
						if((listItem.oldLead == listItem.newLead) && (listItem.oldBid == listItem.newBid) && (listItem.oldConRef == listItem.newConRef) && (listItem.oldCbRef == listItem.newCbRef)){
							return false;
						}
					}
					if(!showLockedRows){	
						if(listItem.isLocked == true){
							return false;
						}
					}

					for(var i = 0; i < searchItems.length; i++){
						var columnName = searchItems[i].columnNameValue;
						var listItemVal = listItem[columnName];
						
						var searchValue;
						
						// check if string or numeric
						if(searchItems[i].searchValueText && searchItems[i].searchValueText != null && searchItems[i].searchValueText != undefined){			
							listItemVal = listItemVal.toLowerCase();
							
							searchValue = searchItems[i].searchValueText.trim();
							searchValue = searchValue.toLowerCase();
						}else if(searchItems[i].searchValueDecimal != null && searchItems[i].searchValueDecimal != undefined){
							searchValue = view._proto.roundByExp(searchItems[i].searchValueDecimal, -2);
							listItemVal = view._proto.roundByExp(listItemVal, -2);
						}
						
						// NO OPERATOR  // date type // 
						// straight comparison between listItem and search value
						if(!searchItems[i].operator || searchItems[i].operator == null || searchItems[i].operator == undefined){			
							if (columnName == "pricingDate" || columnName == "createdOn"){
								var searchFromDate = new Date(view._proto.formatDate(searchItems[i].searchValueDateFrom, true)); // convert to YYYY-MM-DDT00:00:00.000Z
								var searchToDate = new Date(view._proto.formatDate(searchItems[i].searchValueDateTo, true)); // convert to YYYY-MM-DDT00:00:00.000Z
								// Must be of format YYYY-MM-DDT00:00:00.000Z with zeroed out time component in UTC (Z) timezone
								// Reformatting to ensure all dates have zeroed out time component
								var listItemDate = new Date(listItem[columnName]); 
								
								if (listItemDate.getTime() < searchFromDate.getTime() || listItemDate.getTime() > searchToDate.getTime()){
									return false;
								}
							}		
						}
						// WITH OPERATOR //
						else if(searchItems[i].operator && searchItems[i].operator != null && searchItems[i].operator != undefined){
							switch(searchItems[i].operator){
								case "==":
									// multiple entries
									if(columnName == "invoiceId" || columnName == "materialId" || columnName == "customerId" || columnName == "supplierId"){
										var multiples = searchValue.split("\n");
										var multipleFound = false;
										
										for (var j=0; j<multiples.length; j++){
																	
											if ((""+multiples[j]).trim() == listItemVal){
												multipleFound = true;
												break;
											}
										}
										if(multipleFound == false){
											return false;
										}
									}
									// string/numeric
									else{
										if(searchItems[i].searchValueText && searchItems[i].searchValueText != null && searchItems[i].searchValueText != undefined){			
											if(!listItemVal.includes(searchValue)){
												return false;
											}
										}else if(typeof(searchItems[i].searchValueDecimal) != "boolean" && !isNaN(searchItems[i].searchValueDecimal)){
											if(listItemVal != searchValue){
												return false;
											}
										}
									}
								break;
								
								case ">=":
									if(listItemVal < searchValue){
										return false;
									}
								break;
								
								case "<=":
									if(listItemVal > searchValue){
										return false;
									}
								break;
								
								case ">":
									if(listItemVal <= searchValue){
										return false;
									}
								break;
									
								case "<":
									if(listItemVal >= searchValue){
										return false;
									}
								break;
								
								case "!=":
									// multiple entries
									if(columnName == "invoiceId" || columnName == "materialId" || columnName == "customerId" || columnName == "supplierId"){
										var multiples = searchValue.split("\n");
										var multipleFound = false;
										
										for (var j=0; j<multiples.length; j++){
																	
											if ((""+multiples[j]).trim() == listItemVal){
												multipleFound = true;
												break;
											}
										}
										if(multipleFound == true){
											return false;
										}
									}
									// string/numeric
									else{
										if(searchItems[i].searchValueText && searchItems[i].searchValueText != null && searchItems[i].searchValueText != undefined){			
											if(listItemVal.includes(searchValue)){
												return false;
											}
										}else if(typeof(searchItems[i].searchValueDecimal) != "boolean" && !isNaN(searchItems[i].searchValueDecimal)){
											if(listItemVal == searchValue){
												return false;
											}
										}
									}
								break;
							}
						}
					}
					
					return true;
				});
				view._instance.table.setAllRecordsSelected(false, false);
				view.ui.get("Search_Modal").hide(true);	
			},
			clearSearchFilters: function(view){
				view.ui.get("Table").clearSearch();
				view.ui.get("Criteria").clear();
				view.ui.get("Search_Modal").hide(true);
				view._proto.searchTable(view);
				view._proto.addLockedStyled(view, view._instance.table.getPageIndex());
				view._proto.toggleTooltip(view);
			}, 
			/*
				Utility logic to retrieve a record by id.
				This avoids the ongoing cost of iterating through records to find one matching an id
			*/
			getMappedRecord: function(view, id)
			{
				//Lazy-create id to record mappings
				// create at start
				if(view._instance.idMap == null)
				{
					var data = view._instance.table.getRecords();
					var len = data.length;
					
					view._instance.idMap = [];
					
					for(var i = 0; i < len; i++)
					{
						var rowId = data[i].invoiceId + data[i].invoiceLineItemNum; 
						dataElt = data[i];
						view._instance.idMap.push(dataElt);
					}
				}
				
				return view._instance.idMap[id];
			},
			/*
				Resets the record map (called when running a line item query)
			*/
			resetMappedRecords: function(view)
			{
				delete view._instance.idMap;
			},
			//<< Utilities /////////////////////////////////////////////////////////////////////////////
			//>> Query and progress tracking ///////////////////////////////////////////////////////////
			/*
				Stop next query phase from running
			*/
			cancelRequest: function(view)
			{
				view._instance.cancelled = true;
			},
			setProgressActive: function(view, active)
			{
				view._instance.progressCtl.context.options.active.set("value", active);
			},
			setProgress: function(view, progress)
			{
				if(progress == null)
					progress = 0;
				
				var max = view._instance.progressCtl.context.options.maxValue.get("value");

				view._instance.progressCtl.setProgress(progress);
				view._instance.progressBadgeCtl.setText(Math.round(progress / max * 100) + "%");
			},
			/*
				Bootstraps a record with backing methods to to facilitate associating
				a record property with a DOM element representing it
			*/
			setupDataToVisualElements: function(view, record)
			{
				var map = {};
				
				record.getVisualElement = function(propName)
				{
					return map[propName];
				}
				
				record.setVisualElement = function(view, propName, elt)
				{
					map[propName] = elt;
				}
			},
			/*
				Sets the value of a DOM element
			*/
			setElementValue: function(view, propName, elt, value)
			{
				if(value != null)
				{
					elt.innerHTML = view.context.htmlEscape(value); //Escape value for XSS protection
				}
				else
				{
					elt.innerHTML = "";
				}
			},
			/*
				Sets the value of a record property (and fires totals recalculation)
			*/
			setRecordPropValue: function(view, record, propName, value)
			{
				//Set new value in record
				record[propName] = value;
				
				//Recompute total associated with this record property
				this.calculateSelectedTotals(view, propName);
			},
			/*
				Adds click support on a DOM element for in-place editing
			*/
			setupChangeHandler: function(view, type, record, propName, elt, rowId)
			{
				elt.onclick = function()
				{
					view._proto.showValueEditor(view, type, record, propName, elt, rowId);
				}
			},
			//>> Table value editing ///////////////////////////////////////////////////////////////////
			/*
				Display in-place edit field in the place of the DOM element to be edited
			*/
			showValueEditor: function(view, type, record, propName, editedElt, rowId)
			{
				if(view._instance.editor != null)
					throw new Error("Editor already in use");
				
				//Assumes editor CV naming convention <type>Editor
				var editorId = type + "Editor";
				var editorCV = view.ui.get(editorId); //Get editor CV matching the type

				if(editorCV == null)
				{
					throw new Error("Editor [" + editorId + "] not found");
				}
				
				//Setup the editing context
				view._instance.edit = {
					domElt: editedElt,
					editorCV: editorCV,
					propName: propName,
					record: record,
					rowId: rowId
				}
				var newVal = editedElt.innerHTML;
				// remove commas 
				if((type == "decimal" || type == "percent") && newVal != ""){
					newVal = Number(newVal.replace(/[^0-9\.-]+/g,""));
				}
				editorCV.setData(newVal);
				editedElt.parentElement.insertBefore(editorCV.context.element, editedElt);
				domClass.add(editedElt, "editing");
				
				setTimeout(
					function(){view._instance.edit.editorCV.focus()}, 
					100
					);
			},
			/*
				Saves the value entered in the edit field to the record and the DOM element
				and moves the edit CV back to the edit field "pool"
			*/
			resetValueEditor: function(view)
			{
				if(view._instance.edit != null)
				{
					var editorBox = view.context.element.querySelector(".value-editors");
					var value = view._instance.edit.editorCV.getData();
					var type = view._instance.edit.editorCV.context.options._metadata.helpText.get("value");

					editorBox.appendChild(view._instance.edit.editorCV.context.element);
					domClass.remove(view._instance.edit.domElt, "editing");
					
					//Update the value in the data record backing the table row
					this.setRecordPropValue(
						view,
						view._instance.edit.record,
						view._instance.edit.propName,
						value
						);
					
					// add the changed style only if the value was changed
					if(view._instance.edit.domElt.__originalVal != value){
						this.addChangedStyle(view);
					}	
					//format the visual element value
					if(type == "decimal" && value != null){	
						value = view._proto.dollarTerms(value);
					}else if(type == "percent" && value != null){
						value = view._proto.percentTerms(value);
					}
					//Update the value in the DOM element in the cell
					this.setElementValue(
						view, 
						view._instance.edit.propName, 
						view._instance.edit.domElt, 
						value
					);
					
					delete view._instance.edit;
				}
			},
			/*
				Removes the changed style to the row and changes the values back to the 
				__originalVal
			*/
			undoChanges: function(view, row, record, domElt){
				for(var i = 0; i < row.childNodes.length; i++){
					var tdItem = row.childNodes[i];
					
					if(tdItem.lastElementChild){
						if(tdItem.lastElementChild.hasOwnProperty("__originalVal")){
							if(tdItem.lastElementChild.hasOwnProperty("__propName")){
								var propName = tdItem.lastElementChild.__propName; 
								var domElm = tdItem.lastElementChild; //tdItem.getElementsByClassName("editableField")[0];
								var type = domElm.classList[1];

								this.setRecordPropValue(
									view,
									record,
									propName,
									tdItem.lastElementChild.__originalVal
								);
								var originalVal = tdItem.lastElementChild.__originalVal;

								if(type == "decimal" && originalVal != null){	
									originalVal = view._proto.dollarTerms(originalVal);
								}else if(type == "percent" && originalVal != null){
									originalVal = view._proto.percentTerms(originalVal);
								}

								this.setElementValue(
									view, 
									propName, 
									domElm, 
									originalVal
								);
							}
								
						}
					}	
				}
				record.isChanged = false;
				row.classList.remove('editedRow');				
				var btnId = 'undoBtn' + row.id;
				var btn = document.getElementById(btnId);
				btn.remove();
			},
			/* 
				Adds the changed style color to the row and creates the undo button which
				has an on click event to undo the changes
			*/
			addChangedStyle: function(view){
				if(!view._instance.edit.record.isChanged){
					view._instance.edit.record.isChanged = true;
					var row = document.getElementById(view._instance.edit.rowId);
					var record = view._instance.edit.record;
					var domElt = view._instance.edit.domElt; //document.getElementsByClassName("editableField" + row.id);
					
					document.getElementById(view._instance.edit.rowId).classList.add('editedRow');
					
					var undoBtn = document.createElement("button");
					var btnId = 'undoBtn' + row.id;
					
					undoBtn.setAttribute('id', btnId);
					undoBtn.onclick = function(){
						view._proto.undoChanges(view, row, record, domElt);
					}
					var span = document.createElement("span");
					domClass.add(undoBtn, "btn btn-info btn-xs");
					domClass.add(span, "btn-label icon fa fa-undo");
					undoBtn.appendChild(span);
					
					var td = document.getElementById(view._instance.edit.rowId).childNodes[1];
					td.appendChild(undoBtn);
				}
			},
			addLockedStyled: function(view, pageIdx){
				if(pageIdx){
					var propArray = ["newWac", "newBid", "newLead", "newConRef", "newContCogPer", "newItemVarPer", "newWacCogPer", "newItemMkUpPer", "newAwp", "newNoChargeBack", "newOverridePrice"];
					var records = view.ui.get("Table").getRecordsOnPage();
					for(var k = 0; k < records.length; k++){
						var sel = records[k];
						var id = sel.invoiceId + sel.invoiceLineItemNum;
						var row = document.getElementById(id);
						if(sel.isLocked && row.classList.value.indexOf("lockedRow") == -1){
							var td = row.childNodes[1];
							
							row.setAttribute('class', 'lockedRow');
							var lockBtn = document.createElement("button");
							lockBtn.disabled = true;
							lockBtn.style.backgroundColor = "red";
							var span = document.createElement("span");
							
							domClass.add(lockBtn, "btn btn-default btn-xs");
							domClass.add(span, "btn-label icon fa fa-lock");
							
							lockBtn.appendChild(span);
							td.appendChild(lockBtn);

							row.classList.remove('editedRow');				
							var btnId = 'undoBtn' + row.id;
							var btn = document.getElementById(btnId);
							if(btn)
							btn.remove();
							for(var prop in sel){
								for(var j = 0; j < propArray.length; j++){
									if(prop == propArray[j]){
										propName = prop;
										var elt = sel.getVisualElement(propName);
										if(elt == null)
											continue;
										
										var type = elt.__type;										
										var parent = elt.parentNode;
										parent.removeChild(elt)
	
										var value = sel[propName];
										if(type == "decimal" && value != null){	
											value = view._proto.dollarTerms(value);
										}else if(type == "percent" && value != null){
											value = view._proto.percentTerms(value);
										}
										parent.innerHTML += "<div class=nonEditableField>" + value + "</div>"
										//Update record data
										//view._proto.setRecordPropValue(this, record, propName, value);
									}
								}
							}
						}	
					}
				}
			},
			/*
				Checks if the tooltips should be shown based on the Hide Popup Lables checkbox.
				Called from search function, and when the table page is changed. 
			*/
			toggleTooltip: function(view){
				var status = view.ui.get("HideTooltips").isChecked();
				var tooltips = document.getElementsByClassName("tooltiptext");
				if(status){
					for(var i = 0; i < tooltips.length; i++){
						tooltips[i].classList.add("tooltiptextHidden")
					}
				}
				else if(!status){
					for(var i = 0; i < tooltips.length; i++){
						tooltips[i].classList.remove("tooltiptextHidden")
					}
				}
			},
			/*
				Appends the returned records to the table and determines if there are any records left 
				to be returned. If there are, it calls the getCorrectionRows function
			*/
			onLineItemsResult: function(view, result)
			{	
				if(result.length != 0)
				{
					view._instance.progressCtl.setMaximum(view.context.options.totalRecords.get("value"));
				}

				view._instance.offset += view.context.options.numRecords.get("value");
				
				var allRecsLoaded = view._instance.offset >= view.context.options.totalRecords.get("value");
				view._instance.table.appendRecords2(result, allRecsLoaded);
				setTimeout(function(){
					if(!allRecsLoaded)
					{	
						view._proto.setProgress(view, view._instance.offset);
						
						if(!view._instance.cancelled)
						{
							view._proto.getCorrectionRows(view);
						}
						else
						{
							delete view._instance.cancelled;
							view._proto.setProgressActive(view, false);
							view._instance.table.refreshStats();
						}
					}				
					else
					{	// no more records to get					
						view._instance.offset = 0;
						view._proto.setProgress(view, view.context.options.totalRecords.get("value"));
						view._proto.setProgressActive(view, false);
						view.ui.get("Button_Layout").setEnabled(true);
						
						setTimeout(function(){
							view.ui.get("ProgressIcon").setVisible(false); 
							view.ui.get("Progress").setVisible(false);
							view.ui.get("ProgressBadge").setVisible(false);
							view._proto.searchTable(view);
						}, 1500);
					}
				});
			},
			/*
				Calls the service that returns data for the correction rows table. It is called onLoad
				and from the onLineItemsResult function if there are remaining records to be returned
			*/
			getCorrectionRows: function(view){
				if(view._instance.offset == 0)
				{
					view._proto.setProgress(view, 0);
					this.resetMappedRecords(view);
					view._instance.table.clear();
				}
				var input = {processInstanceId: view.context.options.processInstanceId.get("value"),
							lockRequestId: view.context.options.lockRequestId.get("value"),
							numRecords: view.context.options.numRecords.get("value"),
							totalRecords: view.context.options.totalRecords.get("value"),
							rowIndex: view._instance.offset
							};
							
				var serviceArgs = {
					params: JSON.stringify(input),
					load: function(data) {
						if(!data.correctionRow || data.correctionRow.items.length == 0 || data.correctionRow == null){
							view.ui.get("Submit_Button").setEnabled(false);
							var alert = view.ui.get("Alerts");
							alert.context.options.autoFadeDelay.set("value", 0)
							alert.setVisible(true)
							alert.appendAlert("No lines returned in this request.", "Please return or close the request.")
						}else{
							view._proto.onLineItemsResult(view, data.correctionRow.items);
						}
					},
					error: function(e) {
						view.ui.get("Submit_Button").setEnabled(false);
						var alert = view.ui.get("Alerts");
						alert.context.options.autoFadeDelay.set("value", 0)
						alert.setVisible(true)
						alert.appendAlert("The service has failed.", "Admin has been notified.")
					}
				}
				view._proto.setProgressActive(view, true);
				view.context.options.taskDataService(serviceArgs);
			},
			/*
				Called from the Mass Update button click event and opens the update modal
				only if there are any selected records
			*/
			openRecordUpdateDialog: function(view)
			{
				if(view.ui.get("Table").getSelectedRecordCount(true) > 0)
				{
					view.ui.get("Mass_Update_Modal").setVisible(true);
				}
			},
			/*
				Updates the selected record(s) with the updated values specified in the update dialog.
				Because of the layouts used in the modal, it needs to loop through the layouts and find
				all the text control.   
			*/
			updateRecords: function(view)
			{
				var selected = view.ui.get("Table").getSelectedRecords(true);
				var section = view.ui.get("UpdateFieldHolder"); // the parent layout in the modal
				//var hLayouts = [];
				var vLayouts = this.getChildViews(view, section); // the two child layouts in the "UpdateFieldHolder" layout
				var propNameList = [];
				
				for(var i = 0; i < vLayouts.length; i++){
					var current = vLayouts[i];
					for(var subviewId in current.context.subview){ // the subview is a horizontal layout that contains the text and checkbox
						var hl = current.context.getSubview(subviewId)[0];
						var subCV = this.getChildView(view, hl); // the subCv is the text control
						var propName = subCV.context.options._metadata.helpText.get("value");
						
						for(var j = 0; j < selected.length; j++){
							var sel = selected[j];
							var value;
							
							if(subCV.isEnabled()){
								subCV.getData() || subCV.getData() == 0 ? value = subCV.getData() : value = sel[propName];
							}else if(!subCV.isEnabled()){
								value = null;
							}
							sel[propName] = value;
							
							if(!sel.getVisualElement)
								continue;
							
							var elt = sel.getVisualElement(propName);
							
							if(elt == null)
								continue;

							var td = elt.parentElement;
							var rowId = td.parentElement.id;
							
							view._instance.edit = {
								record: sel,
								rowId: rowId
							}
							var type = elt.classList[1];
							if(type == "decimal" && value != null){	
								value = view._proto.dollarTerms(value);
							}else if(type == "percent" && value != null){
								value = view._proto.percentTerms(value);
							}

							this.setElementValue(view, propName, elt, value);
							this.addChangedStyle(view);
							
							delete view._instance.edit;
						}
						subCV.setData(null); //Reset the field to null so it's empty next time						
					}
				}
				
				view.ui.get("Mass_Update_Modal").setVisible(false, true);
				//view._instance.table.setAllRecordsSelected(false, true);
			},
			/*
				Calculates the totals for the Case tab
			*/
			calculateSelectedTotals: function(view){
				var selected = view._instance.table.getSelectedRecords(true);
				var ctx = {};
				
				if(selected != null && selected.length > 0){
					var totals = {};
					totals.selectedCreditTotal = 0;
					totals.selectedRebillTotal = 0;
					totals.selectedNetDiff = 0; // totals.selectedRebillTotal - totals.selectedCreditTotal;
					totals.selectedCBTotal = 0;
					totals.selectedLineCount = 0;
					
					for(var i = 0; i < selected.length; i++){
						totals.selectedCreditTotal += selected[i].oldPrice * selected[i].rebillQty;
						totals.selectedRebillTotal += selected[i].newPrice * selected[i].rebillQty;
						totals.selectedLineCount++; // Atleast 1 line is present for this group
						
						if (!selected[i].newNoChargeBack) {
							totals.selectedCBTotal += ((selected[i].newChargeBack - selected[i].oldChargeBack) * selected[i].rebillQty);	
						}
					}
					totals.selectedNetDiff = totals.selectedCreditTotal - totals.selectedRebillTotal;
					
					view.ui.get("Selected_Credit_Total").setData(totals.selectedCreditTotal);
					view.ui.get("Selected_Rebill_Total").setData(totals.selectedRebillTotal);
					view.ui.get("Selected_Net_Difference").setData(totals.selectedNetDiff);
					view.ui.get("Selected_CB_Total").setData(totals.selectedCBTotal);
					view.ui.get("Selected_Lines_Count").setData(totals.selectedLineCount);
				}
			},
			/*
				Called when a new record is selected and when a tab is switched. Calculates 
				various totals from the selected records
			*/
			groupByCalc: function(view){
				var tabIndex = view.ui.get("TotalTab").getCurrentPane();
				var items = view._instance.table.getRecords();
				
				var GroupBytbl; // The table specific to each tab
				var groupType; // data element that distinguishes the summary group
				var groupName; // display name of data element
				var eventName;
				
				switch (tabIndex){
					case 0:
						view.ui.get("Credit_Total").recalculate();
						view.ui.get("Rebill_Total").recalculate();
						view.ui.get("CB_Total").recalculate();
						view._proto.calculateSelectedTotals(view);
						return;
					case 1:
						GroupBytbl = view.ui.get("CustomerGroupBy/Table1");
						groupType = "customerId"; //data element name
						groupName = "customerName";
						eventName = "CUS_MAT_SUM";
						break;
					case 2:
						GroupBytbl = view.ui.get("SupplierGroupBy/Table1")
						groupType = "supplierId";  //data element name
						groupName = "supplierName";
						eventName = "SUPPLIER_SUM";
						break;
					case 3:
						GroupBytbl = view.ui.get("MaterialGroupBy/Table1");
						groupType = "materialId"; //data element name
						groupName = "materialName";
						eventName = "CUS_MAT_SUM";
						break;
					default:
						return;
				};
				if(items){
					var mapGroup = new Map();
					if (groupType == "supplierId") {
						for(var i = 0; i < items.length; i++) {	
							var summary = {};
							summary.groupBy = items[i][groupType];
							summary.displayName = items[i][groupType] + " " + items[i][groupName];
							summary.oldChargebackTotal = 0;
							summary.newChargebackTotal = 0;
							if (items[i].oldNoChargeBack == "X") { // X means checked in SAP CRM; reversed for testing
								summary.oldChargebackTotal = 0;
								summary.oldNoChargebackCount = 1;
							}
							else {
								summary.oldChargebackTotal = items[i].oldChargeBack * items[i].rebillQty;
								summary.oldNoChargebackCount = 0;
							}
							if (items[i].newNoChargeBack == "X") { // X means checked in SAP CRM; reversed for testing
								summary.newChargebackTotal = 0;
								summary.newNoChargebackCount = 1;
							}
							else {
								summary.newChargebackTotal = items[i].newChargeBack * items[i].rebillQty;
								summary.newNoChargebackCount = 0;
							}
							summary.lineCount = 1; // Atleast 1 line is present for this group
							summary.chargebackTotal = (items[i].newChargeBack - items[i].oldChargeBack) * items[i].rebillQty;

							// Check if record already exists for that customer
							var temp = mapGroup.get(summary.groupBy);
							if (temp) {
								temp.oldChargebackTotal += summary.oldChargebackTotal;
								temp.newChargebackTotal += summary.newChargebackTotal;
								temp.oldNoChargebackCount += summary.oldNoChargebackCount;
								temp.newNoChargebackCount += summary.newNoChargebackCount;
								temp.chargebackTotal += summary.chargebackTotal;
								temp.lineCount += summary.lineCount;
								mapGroup.set(summary.groupBy, temp);
							}
							else {
								mapGroup.set(summary.groupBy, summary);
							}
						}
					}
					else {
						for(var i = 0; i < items.length; i++) {		
							var summary = {};
							summary.groupBy = items[i][groupType];
							summary.displayName = items[i][groupType] + " " + items[i][groupName];
							summary.creditTotal = items[i].oldPrice * items[i].rebillQty;
							summary.rebillTotal = items[i].newPrice * items[i].rebillQty;
							summary.netDiff = summary.creditTotal - summary.rebillTotal;
							summary.lineCount = 1; // Atleast 1 line is present for this group
							// Calc chargeback 
							if (items[i].newNoChargeBack) {
								summary.chargebackTotal = 0; // Should not be undefined even if any particular line has newNoChargeBack flag set
							}
							else {
								summary.chargebackTotal = (items[i].newChargeBack - items[i].oldChargeBack) * items[i].rebillQty;
							}
							// Check if record already exists for that customer
							var temp = mapGroup.get(summary.groupBy);
							if (temp) {
								temp.creditTotal += summary.creditTotal;
								temp.rebillTotal += summary.rebillTotal;
								temp.netDiff += summary.netDiff;
								temp.chargebackTotal += summary.chargebackTotal;
								temp.lineCount += summary.lineCount;
								mapGroup.set(summary.groupBy, temp);
							}
							else {
								mapGroup.set(summary.groupBy, summary);
							}
						}
					}
					var sums = []
					mapGroup.forEach(function(value){
						sums.push(value);
						});
					bpmext.ui.publishEvent(eventName, sums);
				}

			},
			simulatePrice: function(view){
				if (view.ui.get("Table").getSelectedIndices().length > 0){
				
					var rows = view.ui.get("Table").getSelectedRecords(true);
					var priceSimMaxLength = view.context.options.priceSimMaxLength.get("value");
					if (rows.length > priceSimMaxLength) {
						view._proto.populateModalAlerts(view, "overPriceSimulation", priceSimMaxLength);
						return false;
					}
					else {
						view._proto.setProgressBar(view, true);
						var selected = view.ui.get("Table").getSelectedRecords(true); //mix-match work-around to counter 'myList.get("listAllSelectedIndices")' not taking into account filtered table results
						var list = view.ui.get("Table").getRecords();
						var indices = view.ui.get("Table").getSelectedIndices()
						var selectedRowsWithVals = [];
						if (indices && indices.length > 0) {
							for (var i = 0; i < indices.length; i++) {
								for (var j = 0; j < selected.length; j++) {
									if (selected[j].invoiceId == list[indices[i]].invoiceId && selected[j].invoiceLineItemNum == list[indices[i]].invoiceLineItemNum && list[indices[i]] != null && !list[indices[i]].isLocked){
										selectedRowsWithVals.push(list[indices[i]]);   
										break;                                            
									}
								}
							}
						}
						var input = {
							selectedCorrectionRowsJSON: JSON.stringify(selectedRowsWithVals),
							instanceId: view.context.options.processInstanceId.get("value"), //using the 'bpm' context, using the options context variable was bringing unnecessary metadata with it
							isUseOldValues: false //flag to let API know whether this call is being invoked as a background operation (== true) before 'Correct Price' or by user on 'Correct Price' UI (== false)
						};
						var serviceArgs = {
							params: JSON.stringify(input),
							load: function(data) {
								var returned = JSON.stringify(data);
								view._proto.setProgressBar(view, false);
								//console.log("data: ", data.selectedCorrectionRows.correctionRows.items[0])
								var indexedResults = data.selectedCorrectionRows.results.items;
								var simulatedRows = data.selectedCorrectionRows.correctionRows.items;
								if (view._proto.checkForSimFailures(view, indexedResults, simulatedRows)) {
									//var correctionData = _this.context.binding.get("value");
									for (var i = 0; i < selected.length; i++) {
										var sel = selected[i];
															
										for (var j = 0; j < simulatedRows.length; j++) {
											var simulatedRow = simulatedRows[j];
											if ((sel.invoiceId == simulatedRow.invoiceId) && (sel.invoiceLineItemNum == simulatedRow.invoiceLineItemNum)) {                  
			
												for (var propName in sel) {
													if (simulatedRow[propName] != undefined && simulatedRow[propName] != null) { 
													
														sel[propName] = simulatedRow[propName];
													
														var value = sel[propName];
														if(!sel.getVisualElement)
														continue;
														
														var elt = sel.getVisualElement(propName);
														
														if(elt == null)
															continue;
														var type = elt.classList[1];
														if(type == "decimal" && value != null){	
															value = view._proto.dollarTerms(value);
														}else if(type == "percent" && value != null){
															value = view._proto.percentTerms(value);
														}
														
														view._proto.setElementValue(view, propName, elt, value);
													}
												}

											}
										}
									}
									view._proto.populateModalAlerts(view, "success", null);
								}
							},
							error: function(e) {
								//console.log("service call failed:", e);
								view._proto.populateModalAlerts(view, "systemError", null);
								view._proto.setProgressBar(view, false);
							}
						}
						setTimeout(function(){view.context.options.simulatePriceService(serviceArgs);}, 100);    
					}
				}
				else{
					
					alert("Must select at least one row to run Simulate Price.");
					return false;
				}
				
			},
			getFailedItems: function(view, failedRows) {
				var failedRowsStr = "";
				for (var i = 0; i < failedRows.length; i++) {
					failedRowsStr += "\t" + failedRows[i] + "</br>";
				}
				return failedRowsStr;
			},
			checkForSimFailures: function(view, results, items) {
				var isSuccess = true;
				var failedRows = [];
				var failedItem = "";
				if (results && results.length > 0) {
					//console.log("check for sim failures ", JSON.stringify(results));
					for (var i = 0; i < results.length; i++) {
						if (results[i].status == "failure") {
							//console.log("failure at sim", results[i].recordKeys.items);
									isSuccess = false;
									var keys = results[i].recordKeys.items;
							for (var j = 0; j < keys.length; j++) {
								failedRows.push(keys[j]);
							}
						}
					}
						//console.log("failureArray ", failureArray);
				   if (!isSuccess) {
						var failedMessage = "The Price Simulation failed for the Invoice Line Items below.  Therefore, none of the rows were processed.  Please correct the data and try again. \n" + view._proto.getFailedItems(view, failedRows);
						view._proto.populateModalAlerts(view, "failure", failedMessage);
				   }
					return isSuccess;
				}
			},
			/*
				Called from the Return Request button. Restricts returning the request it has been partially submitted
			*/
			checkReturnRequest: function(view) {
				var isPartialSubmitData = view.context.options.get("isPartialSubmit");
				if (isPartialSubmitData == true) {
					view._proto.populateModalAlerts(view, "returnRequestDenial", null);
					return false;
				}
				else {
					view.ui.get("Send_Inquiry_Modal").setVisible(true);
				}
			},
			/*
				Adds the 'Return Request' comment to the BPM Portal comment stream
			*/
			addCommentToStream: function(view) {
				var comment = view.ui.get("Return_Reason").getData();
				var navEvent = view.ui.get("Return_Navigation");
				if (!comment || comment == "") {
					alert("You must enter a reason for returning this request");
				}
				else {
					var taskId = view.context.bpm.system.taskId;
					var restContext = view.context.contextRootMap.rest;
					//console.log("RESTCONTEXT", restContext);
					var url = restContext + "/v1/social/task/" + taskId + "/comment?message=";
					var encodedComment = encodeURI(comment);
					var modalSection = view.ui.get("Send_Inquiry_Modal");
					var xhr = new XMLHttpRequest();
					xhr.responseType = "json";
					xhr.onload = function() {
						var status = xhr.status;
						if(status == 200) {
							var results = xhr.response;
							//console.log("This call worked:", results);
						}
						else {
							console.log(status);
							console.log(xhr.response);
						}
					}
					xhr.open("post", url + encodedComment, true);
					xhr.send(null); 
					modalSection.hide();
					view.context.options.crrbRequest.get("value").set("isReturningRequest", true);
					navEvent.fire();
				}	
			},
			populateModalAlerts: function(view, type, simPriceFailedMsg) {
				var modalAlert = view.ui.get("Modal_Alert1");
				switch(type) {
					case "success":
					modalAlert.setColorStyle("S");
					modalAlert.setTitle("Price Simulation Success!");
					modalAlert.setText("The Price Simulation was completed successfully.");
					modalAlert.setVisible(true);
					break;
					case "overPriceSimulation":
					modalAlert.setColorStyle("G");
					modalAlert.setTitle("Attempt to Simulate Too Many Rows");
					modalAlert.setText("Please execute your Price Simulation on " + simPriceFailedMsg + " rows or less.");
					modalAlert.setVisible(true);
					break;
					case "systemError":
					modalAlert.setColorStyle("G");		
					modalAlert.setTitle("System Service Error");
					modalAlert.setText("The Price Simulation was unsuccessful due to a service failure, your systems administrator has been notified.");
					modalAlert.setVisible(true);
					break;
					case "failure":
					modalAlert.setColorStyle("G");		
					modalAlert.setTitle("Price Simulation Failure");
					modalAlert.setText(simPriceFailedMsg);
					modalAlert.setVisible(true);
					break;
					case "submitNoRows":
					modalAlert.setColorStyle("G");		
					modalAlert.setTitle("Unable to Submit Price Correction");
					modalAlert.setText("You must select at least 1 row before submitting.");
					modalAlert.setVisible(true);
					break;
					case "unqualifiedSubmit":
					modalAlert.setColorStyle("G");		
					modalAlert.setTitle("Unable to Submit Price Correction");
					modalAlert.setText("You must select at least 1 row that is NOT locked and has rebill quantity greater than 0 before submitting.");
					modalAlert.setVisible(true);
					break;
					case "returnRequestDenial":
					modalAlert.setColorStyle("G");		
					modalAlert.setTitle("Unable to Return Request");
					modalAlert.setText("This request cannot be returned as some of the lines have already been submitted for correction.");
					modalAlert.setVisible(true);
					break;
				}
			},
			/*
				Called from the submit button. Check if this is a full or partial submit
			*/
			determineSubmissionType: function(view) {
				var isPartialSubmit = false;
				var diff = 0;
				var filteredSelectedRecords = view.ui.get("Table").getSelectedRecords(true); //mix-match work-around to counter 'myList.get("listAllSelectedIndices")' not taking into account filtered table results
				var listValues = view.ui.get("Table").getRecords();
				var selectedListIndices = view.ui.get("Table").getSelectedIndices() 
				var allSelectedListValues = [];
				var qualifiedListValues = [];
				var qualifiedSelectedListValues = [];
				for (var i = 0; i < listValues.length; i++) {
					if (!listValues[i].isLocked && listValues[i].rebillQty > 0) {
						qualifiedListValues.push(listValues[i]);
					}
				}
				for (var j = 0; j < selectedListIndices.length; j++) {
					for (var k = 0; k < filteredSelectedRecords.length; k++) {
						if (filteredSelectedRecords[k].invoiceId == listValues[selectedListIndices[j]].invoiceId && filteredSelectedRecords[k].invoiceLineItemNum == listValues[selectedListIndices[j]].invoiceLineItemNum) {
							allSelectedListValues.push(listValues[selectedListIndices[j]]);
						}
						if (filteredSelectedRecords[k].invoiceId == listValues[selectedListIndices[j]].invoiceId && filteredSelectedRecords[k].invoiceLineItemNum == listValues[selectedListIndices[j]].invoiceLineItemNum && !listValues[selectedListIndices[j]].isLocked && listValues[selectedListIndices[j]].rebillQty > 0) {
							qualifiedSelectedListValues.push(listValues[selectedListIndices[j]]);
						}
					}
				}
				
				if (selectedListIndices.length == 0) {
					view._proto.populateModalAlerts(view, "submitNoRows", null);
					return false;
				}
				if (qualifiedSelectedListValues.length == 0) {
					view._proto.populateModalAlerts(view, "unqualifiedSubmit", null);
					return false;
				}
				if (qualifiedListValues.length > 0 && qualifiedListValues.length > qualifiedSelectedListValues.length) {
					isPartialSubmit = true;
					var crTot = 0;
					var rbTot = 0;
					var prCRTot = 0;
					var prRBTot = 0;
					var crDiff = 0;
					var rbDiff = 0;
					for (var i = 0; i < qualifiedListValues.length; i++) {
						crTot += qualifiedListValues[i].oldPrice * qualifiedListValues[i].rebillQty;
						rbTot += qualifiedListValues[i].newPrice * qualifiedListValues[i].rebillQty;
					}
					for (var j = 0; j < qualifiedSelectedListValues.length; j++) {
						prCRTot += qualifiedSelectedListValues[j].oldPrice * qualifiedSelectedListValues[j].rebillQty;
						prRBTot += qualifiedSelectedListValues[j].newPrice * qualifiedSelectedListValues[j].rebillQty;
					}
					crDiff = crTot - prCRTot;
					rbDiff = rbTot - prRBTot;
					view._proto.showPartialSubmissionModal(view, crTot, prCRTot, crDiff, rbTot, prRBTot, rbDiff);//, selectedTableRows);
					view.context.options.isPartialSubmit.set("value", isPartialSubmit);
					view.context.options.selectedCorrectionRows.set("value", allSelectedListValues);
					return false;
				}
				else {
					view.context.options.isPartialSubmit.set("value", isPartialSubmit);
					// this object is passed out from the coach, no service is invoked if not a partial submit
					view.context.options.selectedCorrectionRows.set("value", allSelectedListValues);
						return true;
				}
			},
			showPartialSubmissionModal: function(view, crOA, crTot, crDiff, rbOA, rbTot, rbDiff) {
				var modalSection = view.ui.get("Modal_Partial_Submit");
				var crOverallTotalDisp = view.ui.get("Output_Text_Partial_Credit_Total_Total");
				var crTotalDisp = view.ui.get("Output_Text_Partial_Credit_Total");
				var crTotalDiffDisp = view.ui.get("Output_Text_Partial_Credit_Total_Difference");
				var rbOverallTotalDisp = view.ui.get("Output_Text_Partial_Rebill_Total_Total");
				var rbTotalDisp = view.ui.get("Output_Text_Partial_Rebill_Total");
				var rbTotalDiffDisp = view.ui.get("Output_Text_Partial_Rebill_Total_Difference");
				
				crOverallTotalDisp.setText(view._proto.dollarTerms(crOA));
				crTotalDisp.setText(view._proto.dollarTerms(crTot));
				crTotalDiffDisp.setText(view._proto.dollarTerms(crDiff));
				rbOverallTotalDisp.setText(view._proto.dollarTerms(rbOA));
				rbTotalDisp.setText(view._proto.dollarTerms(rbTot));
				rbTotalDiffDisp.setText(view._proto.dollarTerms(rbDiff));
				modalSection.setVisible(true);
			},
			closePartialSubmissionModal: function(view) {
				view.context.options.isPartialSubmit.set("value", false);
				view.ui.get("Modal_Partial_Submit").setVisible(false);
			},
			executePartialSubmission: function(view) {
				var instanceId = view.context.options.processInstanceId.get("value");
				var selectedCorrectionRows = view.ui.get("Table").getSelectedRecords(true);
				//var list = view.ui.get("Table").getRecords();
				//var indices = view.ui.get("Table").getSelectedIndices();
				var actionableCRData = [];	
				//var allCorrectionRowsWithVals = [];
				var crrbRequest = JSON.parse(JSON.stringify(view.context.options.crrbRequest.get("value")));
                var extraKeys = ["childrenCache", "_objectPath", "_systemCallbackHandle", "_watchCallbacks", "_inherited"];
                for (var i = 0; i < extraKeys.length; i++) {
					delete crrbRequest[extraKeys[i]];
				}
                //var selectedRowsObj = JSON.parse(selectedCorrectionRows);
                for (var i = 0; i < selectedCorrectionRows.length; i++) {
                    if (!selectedCorrectionRows[i].isLocked) {
                        var obj = {
                            invoiceId : selectedCorrectionRows[i].invoiceId,
                            invoiceLineItemNum : selectedCorrectionRows[i].invoiceLineItemNum,
                            newWac : selectedCorrectionRows[i].newWac,
                            newBid : selectedCorrectionRows[i].newBid,
                            newLead : selectedCorrectionRows[i].newLead,
                            newConRef : selectedCorrectionRows[i].newConRef,
                            newContCogPer : selectedCorrectionRows[i].newContCogPer,
                            newItemVarPer : selectedCorrectionRows[i].newItemVarPer,
                            newWacCogPer : selectedCorrectionRows[i].newWacCogPer,
                            newItemMkUpPer : selectedCorrectionRows[i].newItemMkUpPer,
                            newAwp : selectedCorrectionRows[i].newAwp,
							newNoChargeBack : selectedCorrectionRows[i].newNoChargeBack,
							//newPONumber : selectedCorrectionRows[i].newPONumber,
                            newOverridePrice : selectedCorrectionRows[i].newOverridePrice
                        }
                        actionableCRData.push(obj);
                    }
                }
				var input = {
					selectedCorrectionRows : actionableCRData,
					instanceId : instanceId,
					crrbRequest : crrbRequest 
				};
				var serviceArgs = {
					params: JSON.stringify(input),
					load: function(data) {
						view._proto.setProgressBar(view, false);
						view._proto.toggleSubmittedRows(view, actionableCRData);
						view._proto.searchTable(view);
						view._proto.addLockedStyled(view, view._instance.table.getPageIndex());
						view._proto.toggleTooltip(view);
					},
					error: function(e) {
						console.log("service call failed: ", e);
						view._proto.setProgressBar(view, false);
					}
				}
				view._proto.setProgressBar(view, true);
				view.context.options.partialSubmissionService(serviceArgs);
				view.ui.get("Modal_Partial_Submit").setVisible(false);
			},
			toggleSubmittedRows: function(view, selectedCRs) {
				var allCRs = view.ui.get("Table").getRecords();
				var propArray = ["newWac", "newBid", "newLead", "newConRef", "newContCogPer", "newItemVarPer", "newWacCogPer", "newItemMkUpPer", "newAwp", "newNoChargeBack", "newOverridePrice"]; // newPONumber

				for (var i = 0; i < allCRs.length; i++) {
					for (var j = 0; j < selectedCRs.length; j++) {
						var curCR = allCRs[i]; 
						if (allCRs[i].invoiceId == selectedCRs[j].invoiceId && allCRs[i].invoiceLineItemNum == selectedCRs[j].invoiceLineItemNum) {
							allCRs[i].isLocked = true;
							allCRs[i].isSubmitted = true;
							allCRs[i].isChanged = false;

							// only the displayed rows need the html changes:
							// add locked style
							var rowId = allCRs[i].invoiceId + allCRs[i].invoiceLineItemNum;
							var row = document.getElementById(rowId);
							//view._proto.setRecordPropValue(this, allCRs[i], isLocked, true)
							if(row != null){
								var td = row.childNodes[1];
								
								row.setAttribute('class', 'lockedRow');
								var lockBtn = document.createElement("button");
								lockBtn.disabled = true;
								lockBtn.style.backgroundColor = "red";
								var span = document.createElement("span");
								
								domClass.add(lockBtn, "btn btn-default btn-xs");
								domClass.add(span, "btn-label icon fa fa-lock");
								
								lockBtn.appendChild(span);
								td.appendChild(lockBtn);

								row.classList.remove('editedRow');				
								var btnId = 'undoBtn' + row.id;
								var btn = document.getElementById(btnId);
								if(btn)
								btn.remove();
								
								// remove editable field
								var sel = allCRs[i];
								for(var prop in sel){
									for(var j = 0; j < propArray.length; j++){
										if(prop == propArray[j]){
											propName = prop;
											var elt = sel.getVisualElement(propName);
											if(elt == null)
												continue;

											console.log(propName, ": ", elt);
											var type = elt.__type;										
											var parent = elt.parentNode;
											parent.removeChild(elt)

											var value = sel[propName];
											if(type == "decimal"){	
												if(value || value == 0)
													value = view._proto.dollarTerms(value);
											}else if(type == "percent"){
												if(value || value == 0)
													value = view._proto.percentTerms(value);
											}else if(!value){
												value = "";
											}
										
											parent.innerHTML += "<div class=nonEditableField>" + value + "</div>";
											
											//Update record data
											//view._proto.setRecordPropValue(this, record, propName, value);
										}
									}
								}
							}
						}
					}
				}
			},
			// REMOVE
			getMappedSubmittedRecords: function(data, id){	
				for(var i = 0; i < data.length; i++){
					var item = data[i];
					if(item.rowId = id){
						return item.record;
					}
				}
			},
			createEditableDiv: function(view, record, propName, type, tableRowId){
				var div = document.createElement("div");
				div.className = "editableField";
				div.classList.add(type);
				var value = record[propName];				

				if(type == "decimal"){	
					if(value || value == 0)
						value = view._proto.dollarTerms(value);
				}else if(type == "percent"){
					if(value || value == 0)
						value = view._proto.percentTerms(value);
				}else if(!value){
					value = "";
				}

				div.__originalVal = record[propName];
				div.__propName = propName;
				div.__type = type;

				this.setElementValue(view, propName, div, value);
				record.setVisualElement(view, propName, div);
				this.setupChangeHandler(view, type, record, propName, div, tableRowId);
				
				return div;
			},
			createNonEditableDiv: function(view, record, propName, type, tableRowId){
				var div = document.createElement("div");
				div.className = "nonEditableField";
				div.classList.add(type);
				var value = record[propName];				

				if(type == "decimal"){	
					if(value || value == 0)
						value = view._proto.dollarTerms(value);
				}else if(type == "percent"){
					if(value || value == 0)
						value = view._proto.percentTerms(value);
				}else if(!value){
					value = "";
				}

				div.__originalVal = record[propName];
				div.__propName = propName;
				div.__type = type;

				this.setElementValue(view, propName, div, value);
				record.setVisualElement(view, propName, div);
				//this.setupChangeHandler(view, type, record, propName, div, tableRowId);
				
				return div;
			},

			setInnerHTML: function(view, td, record, tableRowId, colIndex, header, v1, v2, v3, type, editable){
				var fnFormat;
				if(type == "decimal"){
					fnFormat = view._proto.dollarTerms;
				}else if(type == "percent"){
					fnFormat = view._proto.percentTerms;
				}
				var innerHTML = "";
				var tooltiptext = view.ui.get("Table").getColumns()[colIndex].label;
				innerHTML = "<div class=colgroup><span class=tooltiptext>" + String(tooltiptext) + "</span><div>" + header + "</div>"
				if(type == "string"){
					
					if(record[v1] == "" || record[v1] == undefined){
						innerHTML += "<div>" + "&nbsp" + "</div>";
					}else{
						innerHTML += "<div class=cellValues1>" + record[v1] + "</div>"
					}
					if(!record[v2] || record[v2] == "" || record[v1] == undefined){
						innerHTML += "<div>" + "&nbsp" +  "</div></div>";
					}else{
						innerHTML += "<div class=cellValues2>" + record[v2] + "</div></div>";
					}
				}else{
					if((record[v1] != null && record[v1] != "") || record[v1] == 0){
						innerHTML += "<div class=cellValues1>" + (fnFormat ? fnFormat(record[v1]) : record[v1]) + "</div>"
					}else{
						innerHTML += "<div>" + "&nbsp" + "</div>";
					}
					if((record[v2] != null && record[v2] != "") || record[v2] == 0){
						innerHTML += "<div class=cellValues2>" + (fnFormat ? fnFormat(record[v2]) : record[v2]) + "</div></div>";
					}else{
						innerHTML += "<div>" + "&nbsp" +  "</div></div>";
					}
				}
				td.innerHTML = innerHTML;
				if(record.isLocked || !editable){
					var div = this.createNonEditableDiv(view, record, v3, type, tableRowId);
					td.appendChild(div);
					td.style.verticalAlign = "bottom";
				}else{
					var div = this.createEditableDiv(view, record, v3, type, tableRowId);
					td.appendChild(div);
					td.style.verticalAlign = "bottom";
				}

				return td;
			},
			percentTerms: function(nStr) {
				nStr = Number.parseFloat(nStr).toFixed(3);
				nStr += '';
				var x = nStr.split('.');
				var x1 = x[0];
				var x2 = x.length > 1 ? '.' + x[1] : '';
				var rgx = /(\d+)(\d{3})/;
				while (rgx.test(x1)) {
					x1 = x1.replace(rgx, '$1' + ',' + '$2');
				}
				var x3 = x1 + x2 + "%";
				return x1 + x2 + "%";
			},

			dollarTerms: function(nStr) {
				nStr = Number.parseFloat(nStr).toFixed(2);
				nStr += '';
				var x = nStr.split('.');
				var x1 = x[0];
				var x2 = x.length > 1 ? '.' + x[1] : '';
				var rgx = /(\d+)(\d{3})/;
				while (rgx.test(x1)) {
						x1 = x1.replace(rgx, '$1' + ',' + '$2');
				}

				return "$"+ x1 + x2;
			},
			dateTerms: function (inputDate){
				var newDt = new Date(inputDate);
				newDt = new Date(newDt.getTime() + (newDt.getTimezoneOffset() * 1000)); 
				var dtStr = (newDt.getUTCMonth() + 1)+"/"+newDt.getUTCDate()+"/"+newDt.getUTCFullYear();  
				
				return dtStr;
			},
			roundByExp: function(value, exp) {
				// If the exp is undefined or zero...
				if (typeof exp === 'undefined' || +exp === 0) {
					return Math.round(value);
				}
				value = +value;
				exp = +exp;
				// If the value is not a number or the exp is not an integer...
				if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0)) {
					return NaN;
				}
				// Shift
				value = value.toString().split('e');
				value = Math.round(+(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp)));
				// Shift back
				value = value.toString().split('e');
				return +(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp));
			},
			_onTableCell: function(view, cell)
			{
				var dollarTerms = view._proto.dollarTerms;
				var percentTerms = view._proto.percentTerms;
				var dateTerms = view._proto.dateTerms;
				
				var record = cell.row.data;
				var td = cell.td;
				var colIndex = cell.colIndex;
				var tableRowId = cell.row.data.invoiceId + cell.row.data.invoiceLineItemNum;
				td.parentNode.setAttribute('id', tableRowId);
				switch(colIndex)
				{
					case 0:
						if(record.isLocked){
							td.parentNode.setAttribute('class', 'lockedRow');
							var lockBtn = document.createElement("button");
							lockBtn.disabled = true;
							lockBtn.style.backgroundColor = "red";
							var span = document.createElement("span");
							
							domClass.add(lockBtn, "btn btn-default btn-xs");
							domClass.add(span, "btn-label icon fa fa-lock");
							
							lockBtn.appendChild(span);
							td.appendChild(lockBtn);		
						}
						td.style.verticalAlign = "middle";
						this.setupDataToVisualElements(view, record);
						break;
					case 1:
						var header = "<div class=cellHeader>" + record.customerId + "</div><div class=cellHeader>" + record.customerName + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldWac", "curWac", "newWac", "decimal", true);
						cell.setSortValue(record.customerId);
						break;
					case 2:
						var header = "<div class=cellHeader>" + record.materialId + "</div><div class=cellHeader>" + record.materialName + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldBid", "curBid", "newBid", "decimal", true);
						cell.setSortValue(record.materialId);
						break;
					case 3:
						var header = "<div class=cellHeader>" + dateTerms(record.pricingDate) + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldLead", "curLead", "newLead", "int", true);
						cell.setSortValue(record.pricingDate);
						break;
					case 4: 
						var header = "<div class=cellHeader>" + record.invoiceId + "/" + "</div>"  +  "<div class=cellHeader>" + record.invoiceLineItemNum + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldPrice", "curPrice", "newPrice", "decimal", false);
						cell.setSortValue(record.invoiceId);
						break;
					case 5:
						var header = "<div class=cellHeader>" + record.supplierId + "</div><div class=cellHeader>" + record.supplierName + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldConRef", "curConRef", "newConRef", "string", true);
						cell.setSortValue(record.supplierId);
						break;
					case 6: 
						var header = "<div class=cellHeader>" + record.billQty  + "+ (" + record.retQty + "+" + record.crQty + ")" + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldCbRef", "curCbRef", "newCbRef", "string", false);					
						cell.setSortValue(record.billQty);
						break;
					case 7:
						var header = "<div class=cellHeader>" + record.rebillQty + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldContCogPer", "curContCogPer", "newContCogPer", "percent", true);
						cell.setSortValue(record.rebillQty);
						break;
					case 8:
						var header = "<div class=cellHeader>" + record.uom + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldItemVarPer", "curItemVarPer", "newItemVarPer", "percent", true);
						cell.setSortValue(record.uom);
						break;
					case 9:
						var header = "<div class=cellHeader>" + dateTerms(record.createdOn) + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldWacCogPer", "curWacCogPer", "newWacCogPer", "percent", true);
						cell.setSortValue(record.createdOn);
						break;
					case 10:
						var header = "<div class=cellHeader>" + record.dc + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldItemMkUpPer", "curItemMkUpPer", "newItemMkUpPer", "percent", true);
						cell.setSortValue(record.dc);
						break;
					case 11:
						var header = "<div class=cellHeader>" + record.ndcUpc + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldAwp", "curAwp", "newAwp", "decimal", true);
						cell.setSortValue(record.ndcUpc);
						break;
					case 12:
						var header = "<div class=cellHeader>" + record.billType + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldSellCd", "curSellCd", "newSellCd", "string", false);
						cell.setSortValue(record.poNumber);
						break;	
					case 13:
						var header = "<div class=cellHeader>" + record.chainId + " " + record.chainName + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldNoChargeback", "curNoChargeBack", "newNoChargeBack", "string", true);
						cell.setSortValue(record.ndcUpc);
						break;
					case 14:
						var header = "<div class=cellHeader>" + record.groupId + " " + record.groupName + "/" + "</div><div class=cellHeader>" + record.subgroupId + " " + record.subgroupName + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldActivePrice", "curActivePrice", "newActivePrice", "string", false);
						cell.setSortValue(record.billType);
						break;
					case 15:
						var header = "<div class=cellHeader>" + record.origInvoiceId + "/" + "</div>" +  "<div class=cellHeader>" + record.origInvoiceLineItemNum + "</div>";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldChargeBack", "curChargeBack", "newChargeBack", "decimal", false);	
						cell.setSortValue(record.chainId);
						break;
					case 16:
						var header = "<div class=cellHeader>" + record.orgDbtMemoId + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldSsf", "curSsf", "newSsf", "decimal", false);
						cell.setSortValue(record.groupId);
						break;
					case 17: 
						var header = "<div class=cellHeader>" + dollarTerms(record.orgVendorAccAmt) + "</div>";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldSf", "curSf", "newSf", "decimal", false);
						cell.setSortValue(record.origInvoiceId);
						break;
					case 18:
						var header = "";						
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldListPrice", "curListPrice", "newListPrice", "decimal", false);
						cell.setSortValue(record.orgDbtMemoId);
						break;
					case 19:
						var header = "";	
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "oldOverridePrice", "curOverridePrice", "newOverridePrice", "decimal", true);
						cell.setSortValue(record.orgVendorAccAmt);
						break;
					case 20:
						var header = "";
						td = view._proto.setInnerHTML(view, td, record, tableRowId, colIndex, header, "poNumber", null, "poNumber", "string", true);
						cell.setSortValue(record.oldOverridePrice);
						break;
					default:
						return "H";
				}
				//cell.row.data
				//return null;
			},
			setProgressBar: function(view, status){
				view.ui.get("ProgressModal").setVisible(status);
				view.ui.get("Progress").setVisible(status);
			}
        }
    }
	/*
		Method to get a value from a record by record id and (optionally) by property name.
		If no property name is specified, the entire record is returned
	*/
	this.constructor.prototype.getDataFor = function (id, propName)
	{
		var record = this._proto.getMappedRecord(this, id);
		
		return propName != null ? record[propName] : record;
	}
	
	/*
		Method to set a value in a record by record id and property name
	*/
	this.constructor.prototype.setDataFor = function (id, propName, value)
	{
		var record = this._proto.getMappedRecord(this, id);
		
		//Update record data
		this._proto.setRecordPropValue(this, record, propName, value);
		
		//Update visual representation of the record property if it is displayed in a table row
		if(record.getVisualElement)
		{
			var elt = record.getVisualElement(propName);
			
			this._proto.setElementValue(this, propName, elt, value);
		}
	}   
	
	this.constructor.prototype.getType = function ()
	{
		return "correctionTableCV";
	}

    this.constructor.prototype.load = function ()
    {
		var view = this;
		bpmext.ui.loadView(this);
		
		//Caching references to often-used CV children to avoid recurring lookup overhead
		this._instance.table = this.ui.get("Table");
		this._instance.progressCtl = this.ui.get("Progress");
		this._instance.progressBadgeCtl = this.ui.get("ProgressBadge");
		//Workaround for table performance & selection detection issue
		var view = this;
		
		this._proto.fixTable(this, this._instance.table, function(){
			view._proto.calculateSelectedTotals(view);
		});
		
		this._proto.setProgress(this, 0);
		this._proto.getCorrectionRows(this);

		 
		this._instance.table.constructor.prototype.setPageIndex = this._instance.table.constructor.prototype.setPageIndex2 = function(idx)
		{
			var ps = this.context.options.pageSize.get("value");
			var maxPageIdx = ps > 0 ? Math.ceil(this._instance.list.length() / ps) - 1 : 0;
			
			if(idx > maxPageIdx)
				idx = maxPageIdx;
			else if(idx < 0)
				idx = 0;
			
			this._instance.pageIdx = idx;
			
			if(ps > 0)
			{
				this._proto._reloadList(this, true);
			}
			view._proto.addLockedStyled(view, this._instance.pageIdx);
			view._proto.toggleTooltip(view);
		}
    }
    
    this.constructor.prototype.view = function ()
    {
    }
    
    this.constructor.prototype.change = function (event)
    {
		if(event.type == "config")
		{
			switch (event.property)
			{
				case "_metadata.visibility":
				{
					break;
				}
			}
		}
    }
    
    this.constructor.prototype.unload = function ()
    {
		bpmext.ui.loadView(this);
    }
}