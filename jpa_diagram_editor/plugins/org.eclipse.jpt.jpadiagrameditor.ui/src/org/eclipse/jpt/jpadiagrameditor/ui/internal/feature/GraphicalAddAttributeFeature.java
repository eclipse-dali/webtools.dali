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
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmNullAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants.ShapeType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;

public class GraphicalAddAttributeFeature extends AbstractAddShapeFeature {

	public GraphicalAddAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public PictogramElement add(final IAddContext context) {
		final ContainerShape entityShape = context.getTargetContainer();
		final Wrp wrp = new Wrp();
		TransactionalEditingDomain ted = TransactionUtil
				.getEditingDomain(entityShape);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				PersistentAttribute newAttr = (PersistentAttribute) context
						.getNewObject();
				String txt = JPAEditorUtil.getText(newAttr);
				AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(newAttr);
//				if(!attributeMapping.getKey().equals(newAttr.getJavaPersistentAttribute().getMappingKey())){
////					newAttr.getJavaPersistentAttribute().setMappingKey(attributeMapping.getKey());
//				}
				ContainerShape textShape = null;
				ContainerShape primaryShape = GraphicsUpdater
						.getPrimaryShape(entityShape);
				ContainerShape relationShape = GraphicsUpdater
						.getRelationShape(entityShape);
				ContainerShape basicShape = GraphicsUpdater
						.getBasicShape(entityShape);
				textShape = addAttributeToProperlyShape(entityShape, txt,
						attributeMapping, primaryShape, relationShape, basicShape);
				link(textShape, newAttr);
				layoutPictogramElement(entityShape);
				wrp.setObj(textShape);
			}

		});
		return (PictogramElement) wrp.getObj();
	}

	private ContainerShape addAttributeToProperlyShape(
			ContainerShape entityShape, String txt, AttributeMapping attributeMapping,
			ContainerShape primaryShape, ContainerShape relationShape,
			ContainerShape basicShape) {
		ContainerShape textShape = null;
		if((attributeMapping instanceof IdMapping) || (attributeMapping instanceof EmbeddedIdMapping)) {
			 textShape = addAttributeToShape(entityShape, txt, attributeMapping,
					primaryShape);
		} else if (attributeMapping instanceof SingleRelationshipMapping2_0) {
			DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
			if(identity.usesIdDerivedIdentityStrategy() || identity.usesMapsIdDerivedIdentityStrategy()){
				textShape = addAttributeToShape(entityShape, txt, attributeMapping,
						primaryShape);
			} else {
				textShape = addAttributeToShape(entityShape, txt, attributeMapping,
						relationShape);
			}
		} else if (attributeMapping instanceof RelationshipMapping) {
			textShape = addAttributeToShape(entityShape, txt, attributeMapping,
					relationShape);
		} else {
			textShape = addAttributeToShape(entityShape, txt, attributeMapping,
					basicShape);
		}
		return textShape;
	}

	private ContainerShape addAttributeToShape(ContainerShape entityShape,
			String txt, AttributeMapping attributeMapping, ContainerShape containerShape) {
		ContainerShape textShape = null;
		int childrenSizeBefore = containerShape.getChildren().size();
		int containerHeightBefore = containerShape.getGraphicsAlgorithm()
				.getHeight();
		textShape = addAttribute(getFeatureProvider(), containerShape, txt,
				attributeMapping);

		GraphicsUpdater.updateContainer(containerShape, childrenSizeBefore,
				containerHeightBefore);
		GraphicsUpdater.updateEntityShape(entityShape);

		return textShape;
	}

	@SuppressWarnings("restriction")
	private static ContainerShape addAttribute(IJPAEditorFeatureProvider fp,
			ContainerShape containerShape, String attribTxt,
			AttributeMapping attributeMapping) {

		int width = containerShape.getContainer().getGraphicsAlgorithm()
				.getWidth();
		ContainerShape iconShape = Graphiti.getPeService()
				.createContainerShape(containerShape, false);
		Graphiti.getPeService().setPropertyValue(iconShape,
				JPAEditorConstants.PROP_SHAPE_TYPE, ShapeType.ICON.toString());
		int attribIndex = fp.getAttribsNum(containerShape);
		Rectangle iconRect = UpdateAttributeFeature.addRectangleForIcon(
				iconShape, attribIndex);
		Image icon = null;
		boolean isCollection = JpaArtifactFactory.instance().isCollection(attributeMapping);
		if (isCollection && ((attributeMapping instanceof GenericJavaNullAttributeMapping) 
				|| (attributeMapping instanceof GenericOrmNullAttributeMapping))) {
			icon = Graphiti.getGaService().createImage(iconRect,
					JPAEditorImageProvider.ICON_UNMAPPED);
		} else {
			icon = JPAEditorUtil.createAttributeIcon(iconRect, attributeMapping);
		}
		Graphiti.getGaService().setLocationAndSize(icon,
				JPAEditorConstants.ICON_X, JPAEditorConstants.ICON_Y,
				JPAEditorConstants.ICON_WIDTH, JPAEditorConstants.ICON_HEIGHT);
		ContainerShape textShape = Graphiti.getPeService()
				.createContainerShape(containerShape, false);
		Graphiti.getPeService().setPropertyValue(textShape,
				JPAEditorConstants.PROP_SHAPE_TYPE,
				ShapeType.ATTRIBUTE.toString());
		Rectangle textRectangle = UpdateAttributeFeature.addRectangleForText(
				textShape, attribIndex, width);
		textShape.setActive(true);
		Text text = UpdateAttributeFeature
				.addText(fp, textRectangle, attribTxt);
		Graphiti.getGaService().setWidth(text,
				width - JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER);
		Graphiti.getGaService().setLocationAndSize(text, 1, -2,
				width - JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER,
				JPAEditorConstants.ATTRIBUTE_RECT_HEIGHT);
		fp.increaseAttribsNum(containerShape);
		return textShape;
	}

	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	public boolean canAdd(IAddContext context) {
		return false;
	}
}
