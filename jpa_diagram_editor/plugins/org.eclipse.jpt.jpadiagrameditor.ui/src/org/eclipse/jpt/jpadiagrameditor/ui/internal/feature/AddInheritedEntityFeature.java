/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtilImpl;

public class AddInheritedEntityFeature extends AbstractAddFeature {

	private IJPAEditorImageCreator imageCreator;
	
	public AddInheritedEntityFeature(IFeatureProvider fp) {
		this(fp, new JPAEditorImageCreator(), new JPAEditorUtilImpl());
	}
	

	public AddInheritedEntityFeature(IFeatureProvider fp,
		  IJPAEditorImageCreator imageCreator, 
		  IJPAEditorUtil jpaEditorUtil) {
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
		FreeFormConnection connection = createIsAConnection(addConContext, relation, diagram);
		imageCreator.createIsAConnectionLine(diagram, connection);
		layoutPictogramElement(connection);
		return connection;	
	}
	
	private FreeFormConnection createIsAConnection(IAddConnectionContext addConContext, IsARelation relation,
			final Diagram diagram) {
		FreeFormConnection connection = getFeatureProvider().getPeService().createFreeFormConnection(diagram);
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
        connection.setVisible(true);
        connection.setActive(true);
        getFeatureProvider().getPeServiceUtil().setPropertyValue(connection, IsARelation.IS_A_CONNECTION_PROP_KEY, Boolean.TRUE.toString());
        addDecorators(connection);
		return connection;
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
	private void addDecorators(FreeFormConnection c) {
		ConnectionDecorator d = imageCreator.createArrowConnectionDecorator(c, 0.9999, true);
		Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(), 0, 0);
	}
	
}
