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

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.BidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class DirectEditAttributeFeature extends AbstractDirectEditingFeature {
	
	private boolean isMethodAnnotated = false;

	public DirectEditAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		return true;
	}

	public String getInitialValue(IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		JavaPersistentAttribute jpa = (JavaPersistentAttribute)getFeatureProvider().
											getBusinessObjectForPictogramElement(pe);
		isMethodAnnotated = JpaArtifactFactory.instance().isMethodAnnotated(jpa);
		Text txt = (Text) pe.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0);
		String value =  txt.getValue();
		if (isMethodAnnotated)
			value = JPAEditorUtil.produceValidAttributeName(value);		
		return value;
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (isMethodAnnotated)
			value = JPAEditorUtil.produceValidAttributeName(value);		
		IStatus status = JavaConventions.validateFieldName(value, JavaCore.VERSION_1_5, JavaCore.VERSION_1_5);
		if (!status.isOK())
			return status.getMessage();
		status = checkDuplicateAttribute(value, context);
		if (!status.isOK())
			return status.getMessage();
		return null;
	}

	private IStatus checkDuplicateAttribute(String value, IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		JavaPersistentAttribute oldAt = (JavaPersistentAttribute) getBusinessObjectForPictogramElement(pe);
		JavaPersistentAttribute newAl = (JavaPersistentAttribute)((JavaPersistentType)oldAt.getParent()).getAttributeNamed(value);
		if (newAl != null && !newAl.equals(oldAt)) {
			String message = MessageFormat.format(JPAEditorMessages.DirectEditAttributeFeature_attributeExists, value);
			return new Status(IStatus.ERROR, JPADiagramEditorPlugin.PLUGIN_ID, message);
		}
		return Status.OK_STATUS;
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	public void setValue(String value, IDirectEditingContext context) {
		if (isMethodAnnotated)
			value = JPAEditorUtil.produceValidAttributeName(value);
		PictogramElement pe = context.getPictogramElement();
		JavaPersistentAttribute oldAt = (JavaPersistentAttribute) getBusinessObjectForPictogramElement(pe);

		IRelation rel = getFeatureProvider().getRelationRelatedToAttribute(oldAt);
		String inverseJPTName = null;
		if (BidirectionalRelation.class.isInstance(rel)) 
			inverseJPTName = rel.getInverse().getName();
		if (oldAt.getName().equals(value))
			return;
		try {
			JpaArtifactFactory.instance().renameAttribute(oldAt, value, inverseJPTName, getFeatureProvider());
		} catch (InterruptedException e) {
			return;
		}
		
		if (pe.getGraphicsAlgorithm() == null)
			return;
		final GraphicsAlgorithm alg = pe.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0);
		final String newValue = value;

		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(alg);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				((Text) alg).setValue(newValue);
			}
		});
	}

}
