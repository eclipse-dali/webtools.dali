/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.gen.internal.Association;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;


class AssociationFigure extends Button {
	
	Color enabledColor = new Color( null, 14,66,115);
	Color disabledLineColor = new Color( null, 192,215,231);
	Color selectedColor = new Color( null, 232,232,232 );

	Color selectedBorderColor = new Color( null, 14,66,115 );
	LineBorder selectedBorder = new LineBorder( selectedBorderColor, 2 );
	LineBorder unselectedBorder = new LineBorder( ColorConstants.lightGray, 1 );
	Font descriptionFont = new Font(null, "Arial", 8, SWT.NONE);
	
	/**
	 * The model behind the the view object
	 */
	Association association;
	TableFigure tableFig1;
	TableFigure tableFig2;
	PolylineConnection connection ;
	
	PolygonDecoration referrerDecoration ;	
	PolygonDecoration referencedDecoration ;

	Label referencedLabel ;
	Label referrerLabel ;
	Label descriptionLabel ;
	
	AssociationFigure(Association association, ResourceManager resourceManager) {
		this.association = association;
		
		XYLayout contentsLayout = new XYLayout();
		setLayoutManager(contentsLayout);
		setBorder( unselectedBorder );

		//Create the figures for referrer table and referenced table
		tableFig1 = new TableFigure( association.getReferrerTable().getName(), resourceManager); 
		tableFig2 = new TableFigure( association.getReferencedTable().getName(), resourceManager); 
		
		contentsLayout.setConstraint(tableFig1, new Rectangle(10,10,150,20));
		contentsLayout.setConstraint(tableFig2, new Rectangle(280, 10, 150, 20));

		connection = drawConnection(tableFig1, tableFig2);

		add(tableFig1);
		add(tableFig2);
		add(connection);
		

		descriptionLabel = new Label("");
		contentsLayout.setConstraint(descriptionLabel, new Rectangle(10,30,-1,-1));
		descriptionLabel.setFont( descriptionFont ); 
		add(descriptionLabel);

		//set white background
		this.setBackgroundColor(ColorConstants.white);
		
		update();
		
	}

	private PolylineConnection drawConnection(TableFigure tableFig1,
			TableFigure tableFig2) {
		/* Creating the connection */
		PolylineConnection connection = new PolylineConnection();
		ChopboxAnchor sourceAnchor = new ChopboxAnchor(tableFig1);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(tableFig2);
		connection.setSourceAnchor(sourceAnchor);
		connection.setTargetAnchor(targetAnchor);
		
		/* Creating the decoration */
		referrerDecoration = new SmoothPolygonDecoration();
		connection.setSourceDecoration(referrerDecoration);

		referencedDecoration = new SmoothPolygonDecoration();
		connection.setTargetDecoration(referencedDecoration);
		
		
		/* Adding labels to the connection */
		ConnectionEndpointLocator sourceEndpointLocator = 
			new ConnectionEndpointLocator(connection, false);
		sourceEndpointLocator.setVDistance(-5);
		referrerLabel = new Label("");
		connection.add(referrerLabel, sourceEndpointLocator);

		ConnectionEndpointLocator targetEndpointLocator = 
		        new ConnectionEndpointLocator(connection, true);
		targetEndpointLocator.setVDistance(-5);
		referencedLabel = new Label("");
		connection.add(referencedLabel, targetEndpointLocator);

		ConnectionEndpointLocator relationshipLocator = 
			new ConnectionEndpointLocator(connection,true);
		relationshipLocator.setUDistance(10);
		relationshipLocator.setVDistance(-20);
		Label relationshipLabel = new Label("contains");
		connection.add(relationshipLabel,relationshipLocator);
		return connection;
	}

	public Association getAssociation() {
		return this.association;
	} 
	
	@Override
	public void setSelected ( boolean isSelected ){
		this.setBackgroundColor( isSelected ? selectedColor : ColorConstants.white );
		this.setBorder(isSelected? selectedBorder : unselectedBorder);
	}
	
	/**
	 * Update the view with the changes user made on the model
	 */
	public void update(){
		boolean isGenerated = association.isGenerated();
		
		connection.setForegroundColor( isGenerated? enabledColor: disabledLineColor );
		
		tableFig1.setEnabled(isGenerated);
		tableFig2.setEnabled(isGenerated);
		descriptionLabel.setForegroundColor(isGenerated? enabledColor: disabledLineColor);

		//paintDirectionalityAndCardinality 
		String cardinalityStr;
		String directionality = association.getDirectionality();
		String cardinality = association.getCardinality();
		//Draw referrerRole
		if (cardinality.equals(Association.MANY_TO_ONE) || cardinality.equals(Association.MANY_TO_MANY)) {
			cardinalityStr = "*";
		} else {
			cardinalityStr = "1";
		}
		if (directionality.equals(Association.BI_DI) || directionality.equals(Association.OPPOSITE_DI)) {
			connection.setSourceDecoration(this.referrerDecoration);
		}else{
			connection.setSourceDecoration(null);
		}
		
		this.referrerLabel.setText( cardinalityStr );
			
		//Draw referencedRole
		if (cardinality.equals(Association.MANY_TO_ONE) || cardinality.equals(Association.ONE_TO_ONE)) {
			cardinalityStr = "1";
		} else {
			cardinalityStr = "*";
		}
		if (directionality.equals(Association.BI_DI) || directionality.equals(Association.NORMAL_DI)) {
			connection.setTargetDecoration(this.referencedDecoration);
		}else{
			connection.setTargetDecoration(null);
		}
		this.referencedLabel.setText(cardinalityStr);
		
		String text = "";
		String referrerTableName = association.getReferrerTableName();
		String referencedTable = association.getReferencedTableName();
		if( cardinality.equals(Association.MANY_TO_ONE ) ){
			text = String.format( JptJpaUiWizardsEntityGenMessages.MANY_TO_ONE_DESC, referencedTable , referrerTableName ); 
		}else if( cardinality.equals(Association.ONE_TO_ONE ) ){
			text = String.format( JptJpaUiWizardsEntityGenMessages.ONE_TO_ONE_DESC, referrerTableName, referencedTable );
		}else if( cardinality.equals(Association.MANY_TO_MANY) ){
			text = String.format( JptJpaUiWizardsEntityGenMessages.MANY_TO_MANY_DESC, referrerTableName, referencedTable, referencedTable, referrerTableName );			
		}
		
		if( association.isCustom() ){
			connection.setLineStyle( SWT.LINE_DOT);
		}
		
		this.descriptionLabel.setText(text);
		
	}

	public void dispose() {
		this.descriptionFont.dispose();
		this.selectedBorderColor.dispose();
		this.selectedColor.dispose();
		this.disabledLineColor.dispose();
		this.enabledColor.dispose();
		this.tableFig1.dispose();
		this.tableFig2.dispose();
	}

	public class SmoothPolygonDecoration extends PolygonDecoration
	{
		public SmoothPolygonDecoration()
		{
			super();
		}
		
		@Override
		public void paintFigure(Graphics graphics)
		{
			int savedAntialias = graphics.getAntialias();
			graphics.setAntialias(SWT.ON);
			super.paintFigure(graphics);
			graphics.setAntialias(savedAntialias);
		}
	}
} 
