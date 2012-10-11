/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.gen.internal.ORMGenColumn;
import org.eclipse.jpt.jpa.gen.internal.ORMGenTable;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.ColumnGenPanel;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.TablesAndColumnsCustomizationWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class DynamicTablesAndColumnsCustomizationWizardPage extends
		TablesAndColumnsCustomizationWizardPage {

	public DynamicTablesAndColumnsCustomizationWizardPage(JpaProject jpaProject, ResourceManager resourceManager) {
		super(jpaProject, resourceManager);
	}
	
	@Override
	protected void updateTabelGenDetail(ORMGenTable table) {
		this.selectedTable = table;
		if(tableGenDetatilGroup==null){
			tableGenDetatilGroup = new Composite(detailPanel, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			tableGenDetatilGroup.setLayout(gridLayout);
			
			this.tableGenPanel = new DynamicTableGenPanel(tableGenDetatilGroup, 4 , false, this );

		}
		this.tableGenPanel.setORMGenTable(table);
		this.tableGenPanel.updateControls();
		
		this.detailPanelStatckLayout.topControl = tableGenDetatilGroup;
		this.detailPanel.layout();		

		String baseClass = StringTools.isBlank(table.getExtends()) ? "" : table.getExtends();
		setSuperClass(baseClass, true);			
		setSuperInterfaces(table.getImplements(), true);
		
		detailPanel.getParent().layout();
	}

	@Override
	protected void updateColumnGenDetail(ORMGenColumn column) {
		if(columnGenDetatilGroup==null){
			columnGenDetatilGroup = new Composite(detailPanel, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			columnGenDetatilGroup.setLayout(gridLayout);
			this.columnGenPanel = new ColumnGenPanel(columnGenDetatilGroup, 4, getCustomizer() , this, true);
		}
		columnGenPanel.setColumn(column);
		this.detailPanelStatckLayout.topControl = columnGenDetatilGroup;
		this.detailPanel.layout();		
		detailPanel.getParent().layout();
	}
	
	@Override
	public boolean isDynamic(){
		return true;
	}
	
}
