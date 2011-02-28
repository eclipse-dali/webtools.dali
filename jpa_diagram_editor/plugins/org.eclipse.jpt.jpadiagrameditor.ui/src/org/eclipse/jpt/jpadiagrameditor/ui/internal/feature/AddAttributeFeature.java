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
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;


public class AddAttributeFeature extends AbstractAddShapeFeature {

	private IAddFeature graphicalAdd;

	private ICustomFeature expandCompartmentFeature;

	public AddAttributeFeature(IFeatureProvider fp) {
		this(fp, new GraphicalAddAttributeFeature(fp), new ExpandCompartmentShapeFeature(fp));
	}

	public AddAttributeFeature(IFeatureProvider fp, IAddFeature graphicalAdd, ICustomFeature expandFeature) {
		super(fp);
		this.graphicalAdd = graphicalAdd;
		expandCompartmentFeature = expandFeature;
	}

	public PictogramElement add(final IAddContext context) {
		Object o = context.getNewObject();
		if (!(o instanceof JavaPersistentAttribute)) {
			return null;
		}
		final JavaPersistentAttribute newAttr = (JavaPersistentAttribute) o;
//		JpaArtifactFactory.instance().refreshEntityModel(getFeatureProvider(), (JavaPersistentType)newAttr.getParent());

		getFeatureProvider().putKeyToBusinessObject(getFeatureProvider().getKeyForBusinessObject(newAttr), newAttr);
		PictogramElement pe = getFeatureProvider().getPictogramElementForBusinessObject(newAttr); 
		if (pe != null) 
			return pe;

		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(context.getTargetContainer());
		final Wrp wrp = new Wrp();
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				ContainerShape textShape = graphicalAdd(context.getTargetContainer(), newAttr);
				expand(newAttr);
				enableDirectAfterAdding(textShape);
				wrp.setObj(textShape);
			}
		});
		return (PictogramElement)wrp.getObj();
	}

	private void expand(JavaPersistentAttribute jpa) {
		ContainerShape attributeShape = (ContainerShape) getFeatureProvider().getPictogramElementForBusinessObject(jpa);

		ICustomContext customContext = new CustomContext(new PictogramElement[] { attributeShape.getContainer() });
		expandCompartmentFeature.execute(customContext);
	}

	private ContainerShape graphicalAdd(ContainerShape entityShape, JavaPersistentAttribute newAttr) {
		AddContext context = new AddContext();
		context.setNewObject(newAttr);
		context.setTargetContainer(entityShape);
		ContainerShape textShape = (ContainerShape) graphicalAdd.add(context);
		return textShape;
	}

	private void enableDirectAfterAdding(ContainerShape textShape) {
		GraphicsAlgorithm ga = textShape.getGraphicsAlgorithm();
		final GraphicsAlgorithm graphicsAlgorithm = ga.getGraphicsAlgorithmChildren().get(0);
		if (graphicsAlgorithm instanceof Text) {
			Text text = (Text) graphicsAlgorithm;
			IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
			directEditingInfo.setMainPictogramElement(textShape);
			directEditingInfo.setPictogramElement(textShape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	public boolean canAdd(IAddContext context) {
		Object o = context.getNewObject();
		return o instanceof JavaPersistentAttribute;
	}

}