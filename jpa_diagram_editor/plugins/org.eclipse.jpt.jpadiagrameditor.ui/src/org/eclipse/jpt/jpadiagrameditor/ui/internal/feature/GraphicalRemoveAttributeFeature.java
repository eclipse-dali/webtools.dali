/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


class GraphicalRemoveAttributeFeature extends AbstractCustomFeature {

	public GraphicalRemoveAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public void execute(ICustomContext context) {
		final PictogramElement pe = context.getInnerPictogramElement();
		if (pe == null) 
			return;
		TransactionalEditingDomain ted =  TransactionUtil.getEditingDomain(pe);
		if (ted == null)
			return;
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				Shape sh = (Shape) pe;
				Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(sh);
				if ((bo == null) || (!JavaPersistentType.class.isInstance(bo)))
					return;
				ContainerShape entityShape = (ContainerShape) pe;
				ContainerShape primShape = GraphicsUpdater.getPrimaryShape(entityShape);
				ContainerShape relationShape = GraphicsUpdater.getRelationShape(entityShape);
				ContainerShape basicShape = GraphicsUpdater.getBasicShape(entityShape);

				removeCompartmentChildren(primShape);
				removeCompartmentChildren(relationShape);
				removeCompartmentChildren(basicShape);

				readdCompartmentsChildren((JavaPersistentType) bo, entityShape, primShape, relationShape, basicShape);

				layoutPictogramElement(entityShape);
				reconnect((JavaPersistentType) bo);
			}
		});		

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
			AddConnectionContext ctx = new AddConnectionContext(acSource.getAnchors().iterator().next(), acTarget
					.getAnchors().iterator().next());
			ctx.setNewObject(rel);
			relF.add(ctx);
//			JpaArtifactFactory.instance().refreshEntityModel(fp, jpt);
		}
	}

	@SuppressWarnings("deprecation")
	private void readdCompartmentsChildren(JavaPersistentType javaPersistentType, ContainerShape entityShape,
			ContainerShape primaryShape, ContainerShape relationShape, ContainerShape basicShape) {

		UpdateAttributeFeature updateFeature = new UpdateAttributeFeature(getFeatureProvider());
		updateFeature.addSeparatorsToShape(primaryShape);
		updateFeature.addSeparatorsToShape(relationShape);
		updateFeature.addSeparatorsToShape(basicShape);

		for (JavaPersistentAttribute attribute : javaPersistentType.getAttributes()) {
			updateFeature.addAttributes(entityShape, attribute);

			getFeatureProvider().renewAttributeJoiningStrategyPropertyListener(attribute);
			getFeatureProvider().addJPTForUpdate(javaPersistentType.getName());
		}
	}

	private void removeCompartmentChildren(ContainerShape compartmentShape) {
		int y = 0;
		List<Shape> children = compartmentShape.getChildren();
		for (int i = 0; i < new ArrayList<Shape>(compartmentShape.getChildren()).size(); i++) {
			Shape shape = compartmentShape.getChildren().get(i);
			y = shape.getGraphicsAlgorithm().getY();
			Graphiti.getPeService().deletePictogramElement(shape);
		}
		for (int i = getFeatureProvider().getAttribsNum(compartmentShape); i > 0; i--) {
			Shape shape = compartmentShape.getChildren().get(i);
			y = shape.getGraphicsAlgorithm().getY();
			Graphiti.getPeService().deletePictogramElement(shape);
			getFeatureProvider().decreaseAttribsNum(compartmentShape);
		}
		children = compartmentShape.getChildren();
		shiftUpLowerAttribs(y, children);
		compartmentShape.getGraphicsAlgorithm().setHeight(0);
	}

	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	private void shiftUpLowerAttribs(int y, List<Shape> children) {
		for (int i = 0; i < children.size(); i++) {
			Shape shp = children.get(i);
			if (shp != null) {
				int grY = children.get(i).getGraphicsAlgorithm().getY();
				if (grY > y) {
					children.get(i).getGraphicsAlgorithm().setY(grY - JPAEditorConstants.ATTRIBUTES_PLACEMENT_STEP);
				}
				children.get(i).setVisible(false);
				Graphiti.getPeService().deletePictogramElement(children.get(i));
			}
		}
	}

}