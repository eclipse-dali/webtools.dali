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

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;



public class DirectEditJPAEntityFeature extends AbstractDirectEditingFeature {

	private static String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";	//$NON-NLS-1$

	public DirectEditJPAEntityFeature(IFeatureProvider fp) {
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
	    Shape sh = (Shape)context.getPictogramElement();
	    ContainerShape csh = sh.getContainer();
	    JavaPersistentType jpt = (JavaPersistentType)getBusinessObjectForPictogramElement(csh);
	    return JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(jpt));
	}
	
	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		//CSN #1305664 2010
		PictogramElement pe = context.getPictogramElement();
	    ContainerShape csh = ((Shape)pe).getContainer();	    
	    JavaPersistentType jpt = (JavaPersistentType) getFeatureProvider().getBusinessObjectForPictogramElement(csh);
        String packageName = Signature.getQualifier(jpt.getName());
	    PersistenceUnit unit = jpt.getPersistenceUnit();
        
	    for(Iterator<ClassRef> classRefs = unit.classRefs(); classRefs.hasNext();){
	    	ClassRef classRef = classRefs.next();
	    	if(classRef.getClassName().equals(packageName + Signature.C_DOT + value) && !(JPAEditorUtil.getText(jpt).equals(value))){
		    	return MessageFormat.format(JPAEditorMessages.DirectEditJPAEntityFeature_duplicateEntityName, packageName+value);

	    	}
	    }
	    
	    if (value.length() < 1)
	        return JPAEditorMessages.DirectEditJPAEntityFeature_classNameMsg; 
	    if (value.contains(" ")) //$NON-NLS-1$
	        return JPAEditorMessages.DirectEditJPAEntityFeature_scpacesNotAllowedMsg; 
	    if (value.contains("\n")) //$NON-NLS-1$
	        return JPAEditorMessages.DirectEditJPAEntityFeature_lineBreaksNotAllowedMsg;
	    if(value.contains("{") || value.contains("}")) //$NON-NLS-1$ //$NON-NLS-2$
	    	return JPAEditorMessages.DirectEditJPAEntityFeature_bracesNotAllowedMsg;
	    for (int i = 0; i < value.length(); i++) {
	    	if (allowed.indexOf(value.charAt(i)) < 0)
	    		return MessageFormat.format(JPAEditorMessages.DirectEditJPAEntityFeature_invalidSymbolsMsg, value);	
	    }
	    return null;
	}
	
	public void setValue(final String value, IDirectEditingContext context) {
	    PictogramElement pe = context.getPictogramElement();
	    ContainerShape csh = ((Shape)pe).getContainer();
	    JavaPersistentType jpt = (JavaPersistentType)getBusinessObjectForPictogramElement(csh);
	    Properties props = JPADiagramPropertyPage.loadProperties(jpt.getJpaProject().getProject());

	    String specifiedEntityMappingName = JpaArtifactFactory.instance().getSpecifiedEntityName(jpt);
	    if(specifiedEntityMappingName == null){
	    	RenameEntityWithoutUIFeature ft = new RenameEntityWithoutUIFeature(getFeatureProvider(), value);
	    	ft.execute(jpt);
	    	return;
	    } else {
	    
	    JpaArtifactFactory.instance().renameEntity(jpt, value);
//	    Properties props = JPADiagramPropertyPage.loadProperties(jpt.getJpaProject().getProject());
			if (JPADiagramPropertyPage.doesDirecteEditingAffectClassNameByDefault(jpt.getJpaProject().getProject(), props)) {
				RenameEntityWithoutUIFeature ft = new RenameEntityWithoutUIFeature(getFeatureProvider(), value);
				ft.execute(jpt);
				return;
			}
		}
	    
	    final GraphicsAlgorithm alg = pe.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0);

		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(alg);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				((Text) alg).setValue(value);
			}
		});	    
	    
	    Set<JavaPersistentAttribute> ats = JpaArtifactFactory.instance().getRelatedAttributes(jpt);
	    Iterator<JavaPersistentAttribute> it = ats.iterator();
	    while (it.hasNext()) {
	    	JavaPersistentAttribute at = it.next();
	    	PictogramElement pel = getFeatureProvider().getPictogramElementForBusinessObject(at);
	    	String newAtName = JPAEditorUtil.decapitalizeFirstLetter(value);
	    	if (JpaArtifactFactory.instance().isMethodAnnotated(at)) 
	    		newAtName = JPAEditorUtil.produceValidAttributeName(newAtName);
	    	try {
				newAtName = JpaArtifactFactory.instance().renameAttribute(at, newAtName, jpt.getName(), getFeatureProvider()).getName();
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError(e);
			}
			
	    	final GraphicsAlgorithm algo = pel.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0);
            final String attName = newAtName;
	  		TransactionalEditingDomain tedit = TransactionUtil.getEditingDomain(algo);
	  		tedit.getCommandStack().execute(new RecordingCommand(tedit) {
	  			protected void doExecute() {
	  				((Text) algo).setValue(attName);
	  			}
	  		});	 
	    }
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}	
	
}
