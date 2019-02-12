/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.util.List;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.gen.internal.Association;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/**
 * A Draw2d figure representing list of associations between two database tables
 *
 */
public class AssociationsListComposite extends FigureCanvas {

	List<Association> associations;  
	AssociationToggleSelectionListener listener ;
	TableAssociationsWizardPage tableAssociationsWizardPage; //the parent wizard page
	AssociationFigure selectedAssociationFigure ;
	
	/**
	 * A listener that allows us to stop listening to stuff when the control
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener disposeListener;

	protected final ResourceManager resourceManager;
	
	public AssociationsListComposite(Composite parent, TableAssociationsWizardPage tableAssociationsWizardPage, ResourceManager resourceManager){
		super(parent);
		this.tableAssociationsWizardPage = tableAssociationsWizardPage;
		this.resourceManager = resourceManager;
		
		setBounds(10, 10 , 500, 200);
		Color backgroundColor = new Color(getDisplay(), 255,255,255);
		setBackground(backgroundColor);
		backgroundColor.dispose();
		
		Figure figure = new Figure();
		figure.setLayoutManager(new ToolbarLayout());
		figure.setBorder(new LineBorder(1));
		this.listener = new AssociationToggleSelectionListener();

		this.disposeListener = new ControlDisposeListener();
		this.addDisposeListener(this.disposeListener);
		
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
	
	protected void controlDisposed() {
		this.disposeFigure((Figure) getContents());
		this.removeDisposeListener(this.disposeListener);
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

	private class ControlDisposeListener
		implements DisposeListener {
		public void widgetDisposed(DisposeEvent e) {
			AssociationsListComposite.this.controlDisposed();
		}
	    @Override
		public String toString() {
			return "control dispose listener"; //$NON-NLS-1$
		}
	}
}
