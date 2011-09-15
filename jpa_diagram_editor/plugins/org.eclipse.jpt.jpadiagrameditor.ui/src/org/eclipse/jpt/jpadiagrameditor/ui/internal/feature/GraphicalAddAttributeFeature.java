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

import java.util.HashSet;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants.ShapeType;


public class GraphicalAddAttributeFeature extends AbstractAddShapeFeature {

	public GraphicalAddAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public PictogramElement add(final IAddContext context) {
		final ContainerShape entityShape = context.getTargetContainer();
		final Wrp wrp = new Wrp();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(entityShape);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				JavaPersistentAttribute newAttr = (JavaPersistentAttribute) context.getNewObject();
				String txt = JPAEditorUtil.getText(newAttr);
				HashSet<String> annots = JpaArtifactFactory.instance().getAnnotationNames(newAttr);
				ContainerShape textShape = null;
				ContainerShape primaryShape = GraphicsUpdater.getPrimaryShape(entityShape);
				ContainerShape relationShape = GraphicsUpdater.getRelationShape(entityShape);
				ContainerShape basicShape = GraphicsUpdater.getBasicShape(entityShape);
				textShape = addAttributeToProperlyShape(entityShape, txt, annots, primaryShape, relationShape, basicShape);
				link(textShape, newAttr);
				layoutPictogramElement(entityShape);
				wrp.setObj(textShape);
			}
			
		});
		return (PictogramElement)wrp.getObj();
	}

	private ContainerShape addAttributeToProperlyShape(ContainerShape entityShape, String txt, HashSet<String> annots,
			ContainerShape primaryShape, ContainerShape relationShape, ContainerShape basicShape) {
		ContainerShape textShape = null;
		if (annots.contains(JPAEditorConstants.ANNOTATION_ID) || annots.contains(JPAEditorConstants.ANNOTATION_EMBEDDED_ID)) {
			textShape = addAttributeToShape(entityShape, txt, annots, primaryShape);
		} else if (annots.contains(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)
				|| annots.contains(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)
				|| annots.contains(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)
				|| annots.contains(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			textShape = addAttributeToShape(entityShape, txt, annots, relationShape);
		} else {
			textShape = addAttributeToShape(entityShape, txt, annots, basicShape);
		}
		return textShape;
	}

	private ContainerShape addAttributeToShape(ContainerShape entityShape, String txt, HashSet<String> annots,
			ContainerShape containerShape) {
		ContainerShape textShape = null;
		int childrenSizeBefore = containerShape.getChildren().size();
		int containerHeightBefore = containerShape.getGraphicsAlgorithm().getHeight();
		textShape = addAttribute(getFeatureProvider(), containerShape, txt, annots);

		GraphicsUpdater.updateContainer(containerShape, childrenSizeBefore, containerHeightBefore);
		GraphicsUpdater.updateEntityShape(entityShape);

		return textShape;
	}

	private static ContainerShape addAttribute(IJPAEditorFeatureProvider fp, ContainerShape containerShape,
			String attribTxt, HashSet<String> annotations) {

		int width = containerShape.getContainer().getGraphicsAlgorithm().getWidth();
		ContainerShape iconShape = Graphiti.getPeService().createContainerShape(containerShape, false);
		Graphiti.getPeService().setPropertyValue(iconShape, JPAEditorConstants.PROP_SHAPE_TYPE, ShapeType.ICON.toString());
		int attribIndex = fp.getAttribsNum(containerShape);
		Rectangle iconRect = UpdateAttributeFeature.addRectangleForIcon(iconShape, attribIndex);
		Image icon = null;
		Object ob = fp.getBusinessObjectForPictogramElement(containerShape.getContainer());
		JavaPersistentType jpt = (JavaPersistentType)ob;
		boolean isMethodAnnotated = JpaArtifactFactory.instance().isMethodAnnotated(jpt); 
		boolean isCollection = isMethodAnnotated ? 
				JpaArtifactFactory.instance().isGetterMethodReturnTypeCollection(containerShape.getContainer(), fp, attribTxt) :
				JpaArtifactFactory.instance().isCollection(containerShape.getContainer(), fp, attribTxt); 
		if (isCollection && annotations.isEmpty()) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_UNMAPPED);
		} else{
		    icon = JPAEditorUtil.createAttributeIcon(iconRect, annotations);
		}
		Graphiti.getGaService().setLocationAndSize(icon, JPAEditorConstants.ICON_X, JPAEditorConstants.ICON_Y,
				JPAEditorConstants.ICON_WIDTH, JPAEditorConstants.ICON_HEIGHT);
		ContainerShape textShape = Graphiti.getPeService().createContainerShape(containerShape, false);
		Graphiti.getPeService().setPropertyValue(textShape, JPAEditorConstants.PROP_SHAPE_TYPE, ShapeType.ATTRIBUTE.toString());
		Rectangle textRectangle = UpdateAttributeFeature.addRectangleForText(textShape, attribIndex, width);
		textShape.setActive(true);
		Text text = UpdateAttributeFeature.addText(fp, textRectangle, attribTxt);
		Graphiti.getGaService().setWidth(text, width - JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER);
		Graphiti.getGaService().setLocationAndSize(text, 1, -2, width
				- JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER, JPAEditorConstants.ATTRIBUTE_RECT_HEIGHT);
		fp.increaseAttribsNum(containerShape);
		return textShape;
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	public boolean canAdd(IAddContext context) {
		return false;
	}
}