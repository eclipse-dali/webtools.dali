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
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;


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

	public boolean isAvailable(IContext ctx) {
		return true;
	}
	
	public boolean canExecute(ICustomContext context) {
		return true;
	}
	
	public void execute(ICustomContext context) {
		Diagram d = getDiagram();
		JpaProject project = getTargetJPAProject();
		PersistenceUnit unit = project.getRootContextNode().getPersistenceXml().getPersistence().persistenceUnits().next();
		
		
		Point lowestRightestPointOfExistingDiagram = getLowestRightestPoint(d);
		
		for (int i = 0; i < 200; i++) {
			lowerEdges[i] = lowestRightestPointOfExistingDiagram.y + 
								(((i % 2 == 0) ? 1 : 2) * 
								((lowestRightestPointOfExistingDiagram.y == 0) ? 
									DIST_FROM_EDGE_V : 
									DIST_V));
		}
		IDimension dim = getFeatureProvider().getDiagramTypeProvider().getDiagramEditor().getCurrentSize();
		int dWidth = dim.getWidth();
		numInARow = (dWidth - DIST_FROM_EDGE_H - 20 + JPAEditorConstants.ENTITY_WIDTH)/(JPAEditorConstants.ENTITY_WIDTH + DIST_H);
		numInARow = (numInARow > 0) ? numInARow : 1;
		
		lowerEdges[0] = lowestRightestPointOfExistingDiagram.y + ((lowestRightestPointOfExistingDiagram.y == 0) ? DIST_FROM_EDGE_V : DIST_V);		
		
		for (Iterator<ClassRef> classRefs = unit.classRefs(); classRefs.hasNext();) {
			ClassRef classRef = classRefs.next();
			if (classRef.getJavaPersistentType() != null) { // null if
				JavaPersistentType jpt = classRef.getJavaPersistentType(); 
				if (jpt.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
					PictogramElement pe = getFeatureProvider().getPictogramElementForBusinessObject(jpt);
					if (pe != null)
						continue;
					
					final AddContext ctx = new AddContext();
					ctx.setTargetContainer(d);
					ctx.setNewObject(jpt);
					
					IndexAndLowerEdge ie = getMinLowerEdge();
					
					int x = DIST_FROM_EDGE_H + ie.index * (JPAEditorConstants.ENTITY_WIDTH + DIST_H);
					ctx.setLocation(x, ie.lowerEdge);
					final AddJPAEntityFeature ft = new AddJPAEntityFeature(getFeatureProvider());
					
					final Diagram targetDiagram = (Diagram) ctx.getTargetContainer();
					TransactionalEditingDomain ted = ModelIntegrationUtil.getTransactionalEditingDomain(targetDiagram);
					ted.getCommandStack().execute(new RecordingCommand(ted) {
						@Override
						protected void doExecute() {
							ft.add(ctx);
						}
					});
					ContainerShape entityShape = (ContainerShape)getFeatureProvider().getPictogramElementForBusinessObject(jpt);
					lowerEdges[ie.index] = entityShape.getGraphicsAlgorithm().getY() + entityShape.getGraphicsAlgorithm().getHeight() + DIST_V;
				}
			}
		}		
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
	
}
