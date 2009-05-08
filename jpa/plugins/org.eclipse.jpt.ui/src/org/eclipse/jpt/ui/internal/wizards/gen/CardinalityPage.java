/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import org.eclipse.jpt.gen.internal2.Association;
import org.eclipse.jpt.gen.internal2.ORMGenCustomizer;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class CardinalityPage extends NewAssociationWizardPage {

	private Label mtoDescLabel;
	private Label otmDescLabel;
	private Label otoDescLabel;
	private Label mtmDescLabel;
	
	private Button[] cardinalityButtons = new Button[4];
	
	protected CardinalityPage(ORMGenCustomizer customizer) {
		super( customizer , "CardinalityPage" );
		setTitle(JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_cardinalityPage_title);
		setDescription( JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_cardinalityPage_desc);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 1 ;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_ASSOCIATION_CARDINALITY);

		CardinalitySelectionListener selectionListener = new CardinalitySelectionListener();
		cardinalityButtons[0] = createRadioButton( composite, 1, JptUiEntityGenMessages.manyToOne);
		cardinalityButtons[0].addSelectionListener( selectionListener );
		//Default cardinality is MTO
		cardinalityButtons[0].setSelection(true);
		getWizardDataModel().put(NewAssociationWizard.ASSOCIATION_CADINALITY, Association.MANY_TO_ONE);		

		mtoDescLabel = createLabel(composite,1, JptUiEntityGenMessages.manyToOneDesc);
		
		cardinalityButtons[1] = createRadioButton( composite, 1, JptUiEntityGenMessages.oneToMany);
		cardinalityButtons[1].addSelectionListener( selectionListener );
		
		otmDescLabel = createLabel(composite,1, JptUiEntityGenMessages.manyToOneDesc);

		cardinalityButtons[2] = createRadioButton( composite, 1, JptUiEntityGenMessages.oneToOne);
		cardinalityButtons[2].addSelectionListener( selectionListener );

		otoDescLabel = createLabel(composite,1, JptUiEntityGenMessages.oneToOneDesc);

		cardinalityButtons[3] = createRadioButton( composite, 1, JptUiEntityGenMessages.manyToMany);
		mtmDescLabel= createLabel(composite,1, JptUiEntityGenMessages.manyToManyDesc);
		
		setControl(composite);
		this.setPageComplete( true );

		cardinalityButtons[0].setFocus();

	}

	public void updateWithNewTables() {
		String s1 = getReferrerTableName() ;
		String s2 = getReferencedTableName() ;
		String joinTableName = getJoinTableName();
		if( s1 == null || s2 == null )
			return ;
		
		updateDescriptionText(s1, s2);
		if( joinTableName == null ){
			cardinalityButtons[0].setEnabled(true);
			cardinalityButtons[1].setEnabled(true);
			cardinalityButtons[2].setEnabled(true);
			cardinalityButtons[3].setEnabled(false);
			mtmDescLabel.setEnabled(false);
		}else{
			cardinalityButtons[0].setEnabled(false);
			cardinalityButtons[1].setEnabled(false);
			cardinalityButtons[2].setEnabled(false);
			cardinalityButtons[3].setEnabled(true);
			cardinalityButtons[3].setSelection(true);
			mtmDescLabel.setEnabled(true);
		}
		((Composite)this.getControl()).layout() ;
	}

	private void updateDescriptionText(String s1, String s2) {
		//MTO
		String msg = String.format(JptUiEntityGenMessages.manyToOneDesc, s2, s1);
		mtoDescLabel.setText( msg );
		//OTM
		msg = String.format(JptUiEntityGenMessages.manyToOneDesc, s1, s2);
		otmDescLabel.setText( msg );
		msg = String.format(JptUiEntityGenMessages.oneToOneDesc, s1, s2);
		otoDescLabel.setText( msg );
		msg = String.format(JptUiEntityGenMessages.manyToManyDesc, s1, s2, s2, s1);
		mtmDescLabel.setText( msg );
	}

	public boolean canFlipToNextPage() {
		return false;
	}
	
	private Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		gd.horizontalIndent = 30;
		label.setLayoutData(gd);
		return label;
	}
	
	
	private Button createRadioButton(Composite container, int span, String text ) {
		Button btn = new Button(container, SWT.RADIO );
		btn.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		btn.setLayoutData(gd);
		return btn;
	}
	

	private class CardinalitySelectionListener implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {}
	
		public void widgetSelected(SelectionEvent e) {
			if( e.getSource() == cardinalityButtons[0]){
				getWizardDataModel().put(NewAssociationWizard.ASSOCIATION_CADINALITY, Association.MANY_TO_ONE);
			}else if( e.getSource() == cardinalityButtons[1]){
				getWizardDataModel().put(NewAssociationWizard.ASSOCIATION_CADINALITY, Association.ONE_TO_MANY );
			}else if( e.getSource() == cardinalityButtons[2]){
				getWizardDataModel().put(NewAssociationWizard.ASSOCIATION_CADINALITY, Association.ONE_TO_ONE);
			}else{
				getWizardDataModel().put(NewAssociationWizard.ASSOCIATION_CADINALITY, Association.MANY_TO_MANY);
			}
			CardinalityPage.this.setPageComplete(true);
			
		}
	}
	
}
