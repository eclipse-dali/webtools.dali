/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IAddBendpointFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddBendpointContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorImageCreator;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageCreator;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;
import org.eclipse.swt.graphics.Point;

public class AddInheritedEntityFeature extends AbstractAddFeature {
	
	private static final double START_COEFFICIENT = 0.1;

	private IJPAEditorImageCreator imageCreator;
	
	public AddInheritedEntityFeature(IFeatureProvider fp) {
		this(fp, new JPAEditorImageCreator());
	}
	

	public AddInheritedEntityFeature(IFeatureProvider fp,
		  IJPAEditorImageCreator imageCreator) {
		super(fp);
		this.imageCreator = imageCreator;
	}
	
	
	public boolean canAdd(IAddContext context) {
		return true;
	}

	public PictogramElement add(IAddContext context) {
        final IAddConnectionContext addConContext = (IAddConnectionContext) context;	
		
		final IsARelation relation = (IsARelation)context.getNewObject();
		final Diagram diagram = getDiagram();
		final Wrp wrp = new Wrp();
		
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(diagram);
		RecordingCommand rc = new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				FreeFormConnection connection = createConnection(addConContext, diagram);
				imageCreator.createIsAConnectionLine(diagram, connection);
				getFeatureProvider().putKeyToBusinessObject(relation.getId(), relation);
				link(connection, relation);
				layoutPictogramElement(connection);
				wrp.setObj(connection);
			}			
		};
		try {
		ted.getCommandStack().execute(rc);
		} catch (Exception e){
		
		}
		
		return (PictogramElement)wrp.getObj();
	}
	
	private FreeFormConnection createConnection(IAddConnectionContext addConContext, final Diagram diagram) {
        FreeFormConnection connection = getFeatureProvider().getPeServiceUtil().createFreeFormConnection(diagram);
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
        connection.setVisible(true);
        connection.setActive(true);        
        List<Point> points = JPAEditorUtil.createBendPointList(connection, false);
        for (int i = 0; i < points.size(); i++) {
        	Point p = points.get(i);
        	AddBendpointContext ctx = new AddBendpointContext(connection, p.x, p.y, i);
            IAddBendpointFeature ft = getFeatureProvider().getAddBendpointFeature(ctx);
            ft.addBendpoint(ctx);
        }
        getFeatureProvider().getPeServiceUtil().setPropertyValue(connection, IsARelation.IS_A_CONNECTION_PROP_KEY, Boolean.TRUE.toString());
        addDecorators(connection);

		return connection;
	}
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
	private void addDecorators(FreeFormConnection c) {		
		double startCoefficient = START_COEFFICIENT;
		double endCoefficient = 1.0 - startCoefficient;	
		int len = JPAEditorUtil.calcConnectionLength(c);
		ConnectionDecorator d1 = imageCreator.createIsAArrowConnectionDecorator(c, endCoefficient);
		Graphiti.getGaService().setLocation(d1.getGraphicsAlgorithm(), Math.round(-len/10), 0);
	}
	
}
