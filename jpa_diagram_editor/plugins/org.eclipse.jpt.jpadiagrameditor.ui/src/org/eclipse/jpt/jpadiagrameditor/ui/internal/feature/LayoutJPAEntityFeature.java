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

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IGraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;


public class LayoutJPAEntityFeature extends AbstractLayoutFeature {

	private IPeServiceUtil peUtil = null;

    public LayoutJPAEntityFeature(IJPAEditorFeatureProvider fp) {
        super(fp);
        peUtil = fp.getPeUtil();
    }

    public boolean canLayout(ILayoutContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pe);
        return (bo instanceof JavaPersistentType);
    }

    public boolean layout(ILayoutContext context) {
		boolean anythingChanged = false;
		ContainerShape containerShape = (ContainerShape) context
				.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		if(containerGa.getHeight() < JPAEditorConstants.ENTITY_MIN_HEIGHT){
			containerGa.setHeight(JPAEditorConstants.ENTITY_MIN_HEIGHT);
		}
		
		configureEntityCollapseExpandProperty(containerShape, containerGa);
		
		if (containerGa.getWidth() < JPAEditorConstants.ENTITY_MIN_WIDTH) {
			containerGa.setWidth(JPAEditorConstants.ENTITY_MIN_WIDTH);
			anythingChanged = true;
		}
		int containerWidth = containerGa.getWidth();
		Iterator<Shape> iter = containerShape.getChildren().iterator();
		while (iter.hasNext()) {
			Shape shape = iter.next();
			anythingChanged = layoutContainerEntityChildren(anythingChanged,
					containerWidth, shape);

		}
		Property property = peUtil.getProperty(containerShape, JPAEditorConstants.COLLAPSE_FEATURES);
		if (property == null) {
			IGraphicsUpdater gu = getFeatureProvider().getGraphicsUpdater();
			gu.updateEntityHeigth(containerShape);
		}

		return anythingChanged;
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
    
	private void configureEntityCollapseExpandProperty(
			ContainerShape containerShape, GraphicsAlgorithm containerGa) {
		if(containerGa.getHeight() == JPAEditorConstants.ENTITY_MIN_HEIGHT){
			peUtil.setPropertyValue(containerShape, JPAEditorConstants.COLLAPSE_FEATURES, String.valueOf(JPAEditorConstants.ENTITY_MIN_HEIGHT));
		}
		else if(containerGa.getHeight()>JPAEditorConstants.ENTITY_MIN_HEIGHT){
			peUtil.removeProperty(containerShape, JPAEditorConstants.COLLAPSE_FEATURES);
		}
	}

	private boolean layoutContainerEntityChildren(boolean anythingChanged,
			int containerWidth, Shape shape) {
		GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
		IDimension size = Graphiti.getGaService().calculateSize(graphicsAlgorithm);
		if (containerWidth != size.getWidth()) {
			if (graphicsAlgorithm instanceof Polyline) {
				anythingChanged = layoutPolyline(containerWidth,
						graphicsAlgorithm);
			} else if (graphicsAlgorithm instanceof Rectangle) {
				
				Rectangle rect = (Rectangle) graphicsAlgorithm;
				List<GraphicsAlgorithm> gra = rect.getGraphicsAlgorithmChildren();
				Iterator<GraphicsAlgorithm> it = gra.iterator();
				while (it.hasNext()) {
					GraphicsAlgorithm gr = it.next();
					if (gr instanceof Image){
						layoutHeaderIcon(shape, graphicsAlgorithm);
					}
					else if (gr instanceof Text) {
						Text txt = (Text) gr;
						if(!txt.getFont().isItalic()){
						    layoutHeaderText(containerWidth, graphicsAlgorithm, gr);
						}else{
							layoutAttributesGroups(anythingChanged, gr, containerWidth);
						}
					}					
				}
				anythingChanged = true;
			} 
		}
		return anythingChanged;
	}

	private void layoutHeaderText(int containerWidth,
			GraphicsAlgorithm graphicsAlgorithm, GraphicsAlgorithm gr) {
		Graphiti.getGaService().setWidth(graphicsAlgorithm,
						containerWidth - JPAEditorConstants.HEADER_TEXT_RECT_WIDTH_REDUCER);
		Graphiti.getGaService().setWidth(gr, containerWidth - JPAEditorConstants.HEADER_TEXT_RECT_WIDTH_REDUCER);
	}

	private void layoutHeaderIcon(Shape shape,
			GraphicsAlgorithm graphicsAlgorithm) {
		GraphicsAlgorithm headerIconGraphicsAlg = shape
				.getGraphicsAlgorithm();
		Graphiti.getGaService().setWidth(graphicsAlgorithm, JPAEditorConstants.HEADER_ICON_RECT_WIDTH);
		Graphiti.getGaService().setWidth(headerIconGraphicsAlg, JPAEditorConstants.HEADER_ICON_RECT_HEIGHT);
	}

	private boolean layoutPolyline(int containerWidth,
			GraphicsAlgorithm graphicsAlgorithm) {
		boolean anythingChanged;
		Polyline headerBottomLine = (Polyline) graphicsAlgorithm;
		Point secondPoint = headerBottomLine.getPoints().get(1);
		Point newSecondPoint = Graphiti.getGaService().createPoint(/* headerBottomLine, */
				containerWidth, secondPoint.getY());
		headerBottomLine.getPoints().set(1, newSecondPoint);
		anythingChanged = true;
		return anythingChanged;
	}

	private void layoutAttributesGroups(boolean anythingChanged, GraphicsAlgorithm graphicsAlgorithm,
			int containerWidth) {
		ContainerShape containerShape = (ContainerShape) Graphiti.getPeService().getActiveContainerPe(graphicsAlgorithm);
		layoutConcreteCompartmentShape(anythingChanged, containerShape, containerWidth);
	}

	private void layoutConcreteCompartmentShape(boolean anythingChanged, ContainerShape containerShape,
			int containerWidth) {

		layoutCompartmentShape(containerShape, containerWidth);

		Iterator<Shape> iter = containerShape.getChildren().iterator();
		while(iter.hasNext()) {
			Shape shape = iter.next();
			GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
			if (graphicsAlgorithm instanceof Rectangle) {
				Rectangle rect = (Rectangle) graphicsAlgorithm;
				List<GraphicsAlgorithm> gra = rect.getGraphicsAlgorithmChildren();
				if(gra.isEmpty()){
					Graphiti.getGaService().setWidth(graphicsAlgorithm, containerWidth);
				}
				Iterator<GraphicsAlgorithm> it = gra.iterator();
				while (it.hasNext()) {
					GraphicsAlgorithm gr = it.next();
					if (gr instanceof Image) {
						layoutAttributeIcon(shape, graphicsAlgorithm);
					}
					if (gr instanceof Text) {
						layoutAttributeText(containerWidth, graphicsAlgorithm, gr);
					}
				}
				anythingChanged = true;
			} 
		}
	}

	private void layoutAttributeText(int containerWidth,
			GraphicsAlgorithm graphicsAlgorithm, GraphicsAlgorithm gr) {
		Graphiti.getGaService().setWidth(graphicsAlgorithm,
						containerWidth - JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER);
		Graphiti.getGaService().setWidth(gr,
						containerWidth - JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER);
	}

	private void layoutAttributeIcon(Shape shape,
			GraphicsAlgorithm graphicsAlgorithm) {		
		GraphicsAlgorithm headerIconGraphicsAlg = shape.getGraphicsAlgorithm();
		Graphiti.getGaService().setWidth(graphicsAlgorithm,JPAEditorConstants.ICON_RECT_WIDTH);
		Graphiti.getGaService().setWidth(headerIconGraphicsAlg, JPAEditorConstants.ICON_RECT_WIDTH);
	}

	private void layoutCompartmentShape(ContainerShape containerShape,
			int containerWidth) {
		GraphicsAlgorithm graphic = containerShape.getGraphicsAlgorithm();
		List<GraphicsAlgorithm> graphics = graphic.getGraphicsAlgorithmChildren();
		Iterator<GraphicsAlgorithm> iterator = graphics.iterator();
		while (iterator.hasNext()) {
			GraphicsAlgorithm graphicsAlgorithm = iterator.next();
			Graphiti.getGaService().setWidth(graphic, containerWidth);
			Graphiti.getGaService().setWidth(graphicsAlgorithm, containerWidth);
		}
	}

}
