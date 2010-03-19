/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.List;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.gen.internal.Association;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * A Draw2d figure representing list of associations between two database tables
 *
 */
public class AssociationsListComposite extends FigureCanvas {

	List<Association> associations;  
	AssociationToggleSelectionListener listener ;
	TableAssociationsWizardPage tableAssociationsWizardPage; //the parent wizard page
	AssociationFigure selectedAssociationFigure ;
	
	protected final ResourceManager resourceManager;
	
	public AssociationsListComposite(Composite parent, TableAssociationsWizardPage tableAssociationsWizardPage, ResourceManager resourceManager){
		super(parent);
		this.tableAssociationsWizardPage = tableAssociationsWizardPage;
		this.resourceManager = resourceManager;
		
		setBounds(10, 10 , 500, 200);
		Color backgroundColor = new Color(Display.getDefault(), 255,255,255);
		setBackground(backgroundColor);
		backgroundColor.dispose();
		
		Figure figure = new Figure();
		figure.setLayoutManager(new ToolbarLayout());
		figure.setBorder(new LineBorder(1));
		this.listener = new AssociationToggleSelectionListener(); 
		
		this.setContents(figure);
	}

	public void updateAssociations(List<Association> associations){
		Figure figure = (Figure)this.getContents();
		this.disposeFigure(figure);
		
		this.associations = associations;
		if( associations != null ){
			for( int i = 0; i <associations.size(); i ++ ){
				Association association = associations.get(i);
				AssociationFigure assocFigure = new AssociationFigure(association, this.resourceManager);
				assocFigure.addActionListener( listener );
				figure.add(assocFigure);
			}
		}
	}
	
	public Association getSelectedAssociation(){
		return this.selectedAssociationFigure.getAssociation();
	}

	@SuppressWarnings("unchecked")
	public void updateSelectedAssociation(){
		Figure figure = (Figure)this.getContents();
		List<AssociationFigure> associationFigures = figure.getChildren();
		for(AssociationFigure assocFig : associationFigures){
			if( assocFig == this.selectedAssociationFigure){
				assocFig.update(); 
			}
		}
	}
	
	/**
	 * Get the association just before the selected one in UI
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Association getPreviousAssociation(){
		Figure figure = (Figure)this.getContents();
		List<AssociationFigure> associationFigures = figure.getChildren();
		AssociationFigure ret = null;
		for(AssociationFigure assocFig : associationFigures){
			if( assocFig.isSelected() ){
				break; 
			}
			ret = assocFig;
		}
		return ret==null?null:ret.getAssociation();
	}
	
	@Override
	public void dispose() {
		this.disposeFigure((Figure) getContents());
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	protected void disposeFigure(Figure figure) {
		for (AssociationFigure associationFigure : (List<AssociationFigure>) figure.getChildren()) {
			associationFigure.removeActionListener(this.listener);
			associationFigure.dispose();
		}
		figure.removeAll();
		this.selectedAssociationFigure = null;
	}

	class AssociationToggleSelectionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			AssociationFigure figure = (AssociationFigure )event.getSource() ;
			figure.setSelected(true);
			Association association = figure.getAssociation();
			tableAssociationsWizardPage.updateAssociationEditPanel(association);
			//un-select the previous selected
			if( selectedAssociationFigure != null  && selectedAssociationFigure!= figure ){
				selectedAssociationFigure.setSelected( false );
			}
			//Highlight new selection
			selectedAssociationFigure = figure;
			selectedAssociationFigure.setSelected( true );
		}
	}
}
