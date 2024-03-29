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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants.ShapeType;
import org.eclipse.swt.widgets.Display;


public class GraphicsUpdater {

	private static final int PRIMARY_SHAPE_INDEX = 3;
	private static final int RELATION_SHAPE_CONSTANT = 4;
	private static final int BASIC_SHAPE_INDEX = 5;

	public static int increaseCompartmentHeigth(ContainerShape containerShape,
			int height) {
		for(Shape shape : containerShape.getChildren()){
			Iterator<GraphicsAlgorithm> iterator = shape.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().iterator();
			while(iterator.hasNext()){
			    if(iterator.next() instanceof Text)
			        height = height + JPAEditorConstants.ATTRIBUTES_PLACEMENT_STEP;
			}
		}
		return height;
	}
	
	public static void updateContainer(ContainerShape containerShape, 
			int childrenSizeBefore, int containerHeightBefore){
		int childrenSizeAfter = containerShape.getChildren().size();
		int containerHeightAfter = 0;
		if(childrenSizeBefore == 2){
				containerHeightBefore = JPAEditorConstants.COMPARTMENT_MIN_HEIGHT + JPAEditorConstants.COMPARTMENT_BUTTOM_OFFSET;
		}
		containerHeightAfter = containerHeightBefore + (((childrenSizeAfter - childrenSizeBefore)/2)*JPAEditorConstants.ATTRIBUTES_PLACEMENT_STEP);
		
		if(!isCollapsed(containerShape))
			containerShape.getGraphicsAlgorithm().setHeight(containerHeightAfter);
		else 
			containerShape.getGraphicsAlgorithm().setHeight(JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);
	}
	
	
	public static void updateEntityShape(ContainerShape entityShape){
		ContainerShape primaryShape = getPrimaryShape(entityShape);
		ContainerShape relationShape = getRelationShape(entityShape);
		ContainerShape basicShape = getBasicShape(entityShape);
		
		primaryShape.setVisible(!isEmptyCompartment(primaryShape));
		basicShape.setVisible(!isEmptyCompartment(basicShape));
		relationShape.setVisible(!isEmptyCompartment(relationShape));

        relationShape.getGraphicsAlgorithm().setY(getNextCompartmentY(primaryShape));
        basicShape.getGraphicsAlgorithm().setY(getNextCompartmentY(relationShape));
	}

	private static boolean isEmptyCompartment(ContainerShape primaryShape) {
		return primaryShape.getChildren().size() <= 2;
	}
	
	public static void updateEntityHeigth(ContainerShape entityShape){
		ContainerShape primaryShape = GraphicsUpdater.getPrimaryShape(entityShape);
		ContainerShape relationShape = GraphicsUpdater.getRelationShape(entityShape);
		ContainerShape basicShape = GraphicsUpdater.getBasicShape(entityShape);
		
        entityShape.getGraphicsAlgorithm().setHeight(JPAEditorConstants.ENTITY_MIN_HEIGHT + 
        		primaryShape.getGraphicsAlgorithm().getHeight() + relationShape.getGraphicsAlgorithm().getHeight()
        		+ basicShape.getGraphicsAlgorithm().getHeight() + 2);
	}
	
	public static int getNextCompartmentY(ContainerShape compartmentShape){
		if(isEmptyCompartment(compartmentShape))
			return compartmentShape.getGraphicsAlgorithm().getY();
		return compartmentShape.getGraphicsAlgorithm().getY() + compartmentShape.getGraphicsAlgorithm().getHeight();
	}

	public static ContainerShape getPrimaryShape(ContainerShape entityShape){
		List<Shape> children = entityShape.getChildren();
		if(children.size() < PRIMARY_SHAPE_INDEX+1)
			return null;
		GraphicsAlgorithm alg = children.get(PRIMARY_SHAPE_INDEX).getGraphicsAlgorithm();
		ContainerShape primaryShape = (ContainerShape)Graphiti.getPeService().getActiveContainerPe(alg);
		return primaryShape;
	}
	
	public static ContainerShape getCompartmentSeparatorShape(ContainerShape compartmentShape){
		GraphicsAlgorithm alg = compartmentShape.getChildren().get(0).getGraphicsAlgorithm();
		ContainerShape primarySeparatorShape = (ContainerShape) Graphiti.getPeService().getActiveContainerPe(alg);
		return primarySeparatorShape;
	}
	
	public static ContainerShape getRelationShape(ContainerShape entityShape){
		List<Shape> children = entityShape.getChildren();
		if(children.size()<RELATION_SHAPE_CONSTANT+1)
			return null;
		GraphicsAlgorithm alg = children.get(RELATION_SHAPE_CONSTANT).getGraphicsAlgorithm();
		ContainerShape relationShape = (ContainerShape) Graphiti.getPeService().getActiveContainerPe(alg);
		return relationShape;
	}
	
	public static ContainerShape getBasicShape(ContainerShape entityShape){
		List<Shape> children = entityShape.getChildren();
		if(children.size() < BASIC_SHAPE_INDEX+1)
			return null;
		GraphicsAlgorithm alg = children.get(BASIC_SHAPE_INDEX).getGraphicsAlgorithm();
		ContainerShape basicShape = (ContainerShape) Graphiti.getPeService().getActiveContainerPe(alg);
		return basicShape;
	}
	
	public static boolean isCollapsed(ContainerShape compartmentShape) {
		ContainerShape container = compartmentShape.getContainer();
		String result = null;
		if(compartmentShape.equals(GraphicsUpdater.getPrimaryShape(container)))
			result = Graphiti.getPeService().getPropertyValue(container, JPAEditorConstants.PRIMARY_COLLAPSED);
		else if(compartmentShape.equals(GraphicsUpdater.getBasicShape(container)))
			result = Graphiti.getPeService().getPropertyValue(container, JPAEditorConstants.BASIC_COLLAPSED);
		else if(compartmentShape.equals(GraphicsUpdater.getRelationShape(container)))
			result = Graphiti.getPeService().getPropertyValue(container, JPAEditorConstants.RELATION_COLLAPSED);
		if(Boolean.toString(true).equals(result)) 
			return true;
		return false;
	}
	
	/**
	 * @param compartmentShape
	 * @param collapsed 
	 * @return true if successful
	 */
	public static boolean setCollapsed(ContainerShape compartmentShape, boolean collapsed) {
		ContainerShape container = compartmentShape.getContainer();
		if(compartmentShape.equals(GraphicsUpdater.getPrimaryShape(container))) {
			Graphiti.getPeService().setPropertyValue(container, JPAEditorConstants.PRIMARY_COLLAPSED, Boolean.toString(collapsed));
			return true;
		}
		else if(compartmentShape.equals(GraphicsUpdater.getBasicShape(container))) {
			Graphiti.getPeService().setPropertyValue(container, JPAEditorConstants.BASIC_COLLAPSED, Boolean.toString(collapsed));
			return true;
		}
		else if(compartmentShape.equals(GraphicsUpdater.getRelationShape(container))) {
			Graphiti.getPeService().setPropertyValue(container, JPAEditorConstants.RELATION_COLLAPSED, Boolean.toString(collapsed));
			return true;
		}
		return false;
	}
	
	public static void updateHeader(ContainerShape entityShape, final String newHeader) {
		final Text txt = getHeaderText(entityShape);
		if (txt == null)
			return;
//		if (!JPAEditorUtil.areHeadersEqual(txt.getValue(), newHeader)) {

		if (!txt.getValue().equals(newHeader)) {
			final TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(txt);
			final RecordingCommand rc = new RecordingCommand(ted) {
				@Override
				protected void doExecute() {
					txt.setValue(newHeader);		
				}		
			};	
			
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					ted.getCommandStack().execute(rc);
				}
			});
		}
	}
	
	private static Text getHeaderText(ContainerShape entityShape) {
		if(entityShape == null)
			return null;		
		List<Shape> shapes = entityShape.getChildren();
		Iterator<Shape> shIt = shapes.iterator();
		Shape headerShape = null;
		while (shIt.hasNext()) {
			headerShape = shIt.next();
			String shapeType = Graphiti.getPeService().getPropertyValue(headerShape, JPAEditorConstants.PROP_SHAPE_TYPE);
			if (ShapeType.HEADER.toString().equals(shapeType)) 
				break;
			headerShape = null;
		}
		if (headerShape == null)
			return null;
		GraphicsAlgorithm ga = headerShape.getGraphicsAlgorithm();
		if (ga == null)
			return null;
		if (ga.getGraphicsAlgorithmChildren().size() == 0)
			return null;
		Text txt = (Text)ga.getGraphicsAlgorithmChildren().get(0);
		return txt;
	}
}
