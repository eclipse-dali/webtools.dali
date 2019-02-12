/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
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

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class AddAllEntitiesFeature extends AbstractCustomFeature implements IAddFeature {

	int[] lowerEdges = new int[200]; 
	int numInARow;
	private final static int DIST_FROM_EDGE_H = 50;
	private final static int DIST_FROM_EDGE_V = 50;
	private final static int DIST_H = 100;
	private final static int DIST_V = 100;
	
	public AddAllEntitiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean isAvailable(IContext ctx) {
		return true;
	}
	
	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
	
	public void execute(ICustomContext context) {
		Diagram d = getDiagram();
		JpaProject project = getTargetJPAProject();
		PersistenceUnit unit = project.getContextRoot().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		
		
		Point lowestRightestPointOfExistingDiagram = getLowestRightestPoint(d);
		
		for (int i = 0; i < 200; i++) {
			lowerEdges[i] = lowestRightestPointOfExistingDiagram.y + 
								(((i % 2 == 0) ? 1 : 2) * 
								((lowestRightestPointOfExistingDiagram.y == 0) ? 
									DIST_FROM_EDGE_V : 
									DIST_V));
		}
		
		GraphicsAlgorithm graphicsAlgorithm = getFeatureProvider().getDiagramTypeProvider().getDiagram().getGraphicsAlgorithm();
		IDimension dim = Graphiti.getGaService().calculateSize(graphicsAlgorithm);
		int dWidth = dim.getWidth();
		numInARow = (dWidth - DIST_FROM_EDGE_H - 20 + JPAEditorConstants.ENTITY_WIDTH)/(JPAEditorConstants.ENTITY_WIDTH + DIST_H);
		numInARow = (numInARow > 0) ? numInARow : 1;
		
		lowerEdges[0] = lowestRightestPointOfExistingDiagram.y + ((lowestRightestPointOfExistingDiagram.y == 0) ? DIST_FROM_EDGE_V : DIST_V);		
		TransactionalEditingDomain ted = ModelIntegrationUtil.getTransactionalEditingDomain(d);
		
		showAllJavaPersistentTypes(d, unit, ted);
		
		for(MappingFileRef mappingFileRef : unit.getMappingFileRefs()) {
			showAllOrmPersistentTypes(d, mappingFileRef, ted);
		}
		
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				JpaArtifactFactory.instance().rearrangeIsARelations(getFeatureProvider());
			}
		});

	}

	private void showAllJavaPersistentTypes(Diagram d, PersistenceUnit unit,
			TransactionalEditingDomain ted) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) { // null if
				PersistentType jpt = classRef.getJavaPersistentType(); 
				if (JpaArtifactFactory.instance().isAnyKindPersistentType(jpt)) {
					addPersistentTypeInDiagram(d, ted, jpt);
				}
			}
		}
	}
	
	private void showAllOrmPersistentTypes(Diagram d, MappingFileRef ormlXml,
			TransactionalEditingDomain ted) {
		for (PersistentType jpt : ormlXml.getPersistentTypes()) {
			if (JpaArtifactFactory.instance().isAnyKindPersistentType(jpt)) {
				addPersistentTypeInDiagram(d, ted, jpt);
			}
		}
	}

	private void addPersistentTypeInDiagram(Diagram d,
			TransactionalEditingDomain ted, PersistentType jpt) {
		PictogramElement pe = getFeatureProvider().getPictogramElementForBusinessObject(jpt);
		if (pe != null)
			return;
		
		final AddContext ctx = new AddContext();
		ctx.setTargetContainer(d);
		ctx.setNewObject(jpt);
		
		IndexAndLowerEdge ie = getMinLowerEdge();
		
		int x = DIST_FROM_EDGE_H + ie.index * (JPAEditorConstants.ENTITY_WIDTH + DIST_H);
		ctx.setLocation(x, ie.lowerEdge);
		final AddJPAEntityFeature ft = new AddJPAEntityFeature(getFeatureProvider(), false);
		
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				ft.add(ctx);
			}
		});
		ContainerShape entityShape = (ContainerShape)getFeatureProvider().getPictogramElementForBusinessObject(jpt);
		lowerEdges[ie.index] = entityShape.getGraphicsAlgorithm().getY() + entityShape.getGraphicsAlgorithm().getHeight() + DIST_V;
	}
	
	private IndexAndLowerEdge getMinLowerEdge() {
		IndexAndLowerEdge res = new IndexAndLowerEdge();
		res.lowerEdge = 10000000;
		for (int i = 0; i < numInARow; i++) {
			if (lowerEdges[i] < res.lowerEdge) {
				res.lowerEdge = lowerEdges[i];
				res.index = i;
			}
		}
		return res;
	}
	
	private JpaProject getTargetJPAProject() {
		return ModelIntegrationUtil.getProjectByDiagram(getDiagram().getName());
	}
	
	public static Point getLowestRightestPoint(Diagram d) { 
		List<Shape> shapes = d.getChildren();
		Iterator<Shape> it = shapes.iterator();
		int x = 0;
		int y = 0;
		while (it.hasNext()) {
			Shape sh = it.next();
			int rightEdge = sh.getGraphicsAlgorithm().getX() + 
							sh.getGraphicsAlgorithm().getWidth();
			x = Math.max(x, rightEdge);
			int lowerEdge = sh.getGraphicsAlgorithm().getY() +
							 sh.getGraphicsAlgorithm().getHeight();
			y = Math.max(y, lowerEdge);
		}
		x = Math.max(x, 3 * (DIST_H + JPAEditorConstants.ENTITY_WIDTH));
		return new Point(x, y);
	}

	public PictogramElement add(IAddContext context) {
		this.execute(new CustomContext());
		return null;
	}

	public boolean canAdd(IAddContext context) {
		return true;
	}
	
	private class IndexAndLowerEdge {
		int index;
		int lowerEdge;
	}
	
	@Override
	public String getName() {
		return JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities;
	}
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	
}
