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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class UpdateAttributeFeature extends AbstractCustomFeature {

	public UpdateAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public void reconnect(JavaPersistentType jpt) {
		IJPAEditorFeatureProvider fp = getFeatureProvider();
		Collection<IRelation> rels = JpaArtifactFactory.instance().produceAllRelations(jpt, fp);
		JpaArtifactFactory.instance().refreshEntityModel(fp, jpt);

		Iterator<IRelation> it = rels.iterator();
		while (it.hasNext()) {
			IRelation rel = it.next();
			AddRelationFeature relF = new AddRelationFeature(fp);
			AnchorContainer acSource = (AnchorContainer) fp.getPictogramElementForBusinessObject(rel.getOwner());
			AnchorContainer acTarget = (AnchorContainer) fp.getPictogramElementForBusinessObject(rel.getInverse());
			if ((acSource != null) && (acTarget != null)) { 
				AddConnectionContext ctx = new AddConnectionContext(acSource.getAnchors().iterator().next(), acTarget
					.getAnchors().iterator().next());
				ctx.setNewObject(rel);
				relF.add(ctx);
			}
		}
	}

	/**
	 * @deprecated use the {@link GraphicalRemoveAttributeFeature}
	 */
	public void execute(ICustomContext context) {
		GraphicalRemoveAttributeFeature remove = new GraphicalRemoveAttributeFeature(getFeatureProvider());
		remove.execute(context);
	}

	/**
	 * @deprecated Use GraphicalAddAttributeFeature
	 * @param entityShape
	 * @param newAttr
	 * @return
	 */
	public ContainerShape addAttributes(ContainerShape entityShape, JavaPersistentAttribute newAttr) {
		AddContext context = new AddContext();
		context.setNewObject(newAttr);
		context.setTargetContainer(entityShape);
		GraphicalAddAttributeFeature feature = new GraphicalAddAttributeFeature(getFeatureProvider());
		return (ContainerShape) feature.add(context);
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	public static Text addText(IFeatureProvider fp, Rectangle textRectangle, String txt) {
		Text text = Graphiti.getGaService().createDefaultText(textRectangle, txt);
		Color color = Graphiti.getGaService().manageColor(fp.getDiagramTypeProvider().getDiagram(),
				JPAEditorConstants.ENTITY_TEXT_FOREGROUND);
		text.setForeground(color);
		text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		text.getFont().setBold(true);
		return text;
	}

	public static Rectangle addRectangleForText(ContainerShape cs, int attribIndex, int width) {
		Rectangle rect = Graphiti.getGaService().createRectangle(cs);
		rect.setFilled(false);
		rect.setLineVisible(false);
		rect.setHeight(JPAEditorConstants.ATTRIBUTE_RECT_HEIGHT);
		rect.setWidth(width - JPAEditorConstants.ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER);
		rect.setX(JPAEditorConstants.ATTRIBUTE_TEXT_RECT_X);
		rect
				.setY(JPAEditorConstants.ATTRIBUTES_TOP_OFFSET + attribIndex
						* JPAEditorConstants.ATTRIBUTES_PLACEMENT_STEP);
		return rect;
	}

	public static Rectangle addRectangleForIcon(ContainerShape cs, int attribIndex) {
		Rectangle rect = Graphiti.getGaService().createRectangle(cs);
		rect.setFilled(false);
		rect.setLineVisible(false);
		rect.setHeight(JPAEditorConstants.ICON_RECT_HEIGHT);
		rect.setX(JPAEditorConstants.ICON_RECT_LEFT_OFFSET);
		rect.setWidth(JPAEditorConstants.ICON_RECT_WIDTH);
		rect
				.setY(JPAEditorConstants.ATTRIBUTES_TOP_OFFSET + attribIndex
						* JPAEditorConstants.ATTRIBUTES_PLACEMENT_STEP);
		return rect;
	}

	public void addSeparatorsToShape(ContainerShape compartmentShape) {
		addSeparatorToCollection(compartmentShape, 0);
		addSeparatorToCollection(compartmentShape, JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);
	}

	private Shape addSeparatorToCollection(ContainerShape containerShape, int y) {
		final int width = containerShape.getGraphicsAlgorithm().getWidth();
		Shape shape = Graphiti.getPeService().createShape(containerShape, false);
		Rectangle rectangle = Graphiti.getGaService().createRectangle(shape);
		rectangle.setForeground(manageColor(JPAEditorConstants.ENTITY_BACKGROUND));
		rectangle.setBackground(manageColor(JPAEditorConstants.ENTITY_BORDER_COLOR));
		rectangle.setLineVisible(false);
		Graphiti.getGaService().setSize(rectangle, width, JPAEditorConstants.SEPARATOR_HEIGHT);
		Graphiti.getGaService().setLocationAndSize(rectangle, 0, y, width, JPAEditorConstants.SEPARATOR_HEIGHT);
		return shape;
	}
}
