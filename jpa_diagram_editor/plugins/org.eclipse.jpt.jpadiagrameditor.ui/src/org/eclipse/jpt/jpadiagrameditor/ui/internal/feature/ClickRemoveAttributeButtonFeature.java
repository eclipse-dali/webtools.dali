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
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.internal.features.context.impl.base.PictogramElementContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandContext;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.command.DeleteAttributeCommand;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;



@SuppressWarnings("restriction")
public class ClickRemoveAttributeButtonFeature extends DefaultDeleteFeature {

	private String attrName = ""; //$NON-NLS-1$
	
	public ClickRemoveAttributeButtonFeature(IFeatureProvider provider) {
		super(provider);
	}	
		
    protected String getQuestionToUser() {
    	return MessageFormat.format(JPAEditorMessages.ClickRemoveAttributeButtonFeature_deleteAttributeQuestion, new Object[] {attrName}); 
    }

	public boolean canUndo(IContext context) {
		return false;
	}
	
	private String getAttrName(ContainerShape textShape) {
		String txt = ((Text)(textShape.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0))).getValue();
		String attrName = txt.substring(txt.indexOf(':') + 1);
		attrName = attrName.trim();	
		return attrName;
	}
		
	private void deleteAttribute(ContainerShape pe, String attrName) {
		PersistentType jpt = (PersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(pe.getContainer().getContainer());
		deleteFieldFromCompositePKClass(attrName, jpt);
		JpaArtifactFactory.instance().deleteAttribute(jpt, attrName, getFeatureProvider());
	}
	
    @Override
	public void delete(IDeleteContext context) {
    	delete(context, true);
    }
    
	@Override
	public void preDelete(IDeleteContext context) {
		super.preDelete(context);
	}
    
    public void delete(IDeleteContext context, boolean haveToAsk) {
		PictogramElementContext ctx = (PictogramElementContext)context;
		ContainerShape textShape = (ContainerShape)ctx.getPictogramElement();	
		if ((textShape == null) || textShape.getGraphicsAlgorithm() == null) 
			return;
		String attrName = getAttrName(textShape);
		this.attrName = attrName;
    	if (haveToAsk) {
    		if (!getUserDecision(context)) {
    			return;
    		}
    	}
    	
        preDelete(context); 
        if (textShape.getGraphicsAlgorithm() == null){
        	return;
        }  
        
		deleteAttribute(textShape, attrName);
    }

    
    @Override
	protected void deleteBusinessObjects(Object[] businessObjects) {
        if (businessObjects != null) {
            for (Object bo : businessObjects) {
                deleteBusinessObject(bo);
            }
        }
    }    
		
	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public String getName() {
		return JPAEditorMessages.ClickRemoveAttributeButtonFeature_createAttributeButtonlabel; 
	}

	@Override
	public String getDescription() {
		return JPAEditorMessages.ClickRemoveAttributeButtonFeature_createAttributeButtonDescription; 
	}
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}

	/**
	 * Returns a collection of java persistent types, that have an embedded id attribute of type, the given
	 * fully qualified name, or an id class annotation with the given fully qualified name.
	 * @param jpt - the java persistent type from which the attribute is originally deleted
	 * @param fqn - te fully qualified name of the composite primary key class
	 * @return a collection of java persistent types that uses the same composite primary key class.
	 */
	private Set<PersistentType> getAllJPTWithSameIDClassOrEmbeddedId(PersistentType jpt, String fqn){
		HashSet<PersistentType> persistentTypes = new HashSet<PersistentType>();
		ListIterator<PersistenceUnit> lit = jpt.getJpaProject().getContextModelRoot().getPersistenceXml().getRoot().getPersistenceUnits().iterator();		
		PersistenceUnit pu = lit.next();
		for(PersistentType persistentType : pu.getPersistentTypes()){
			if(!persistentType.getName().equals(jpt.getName())  && ((hasSameEmbeddedId(persistentType, fqn)) || hasSameIdClass(persistentType, fqn))){
				persistentTypes.add(persistentType);
			}
		}
		return persistentTypes;
	}
	
	/**
	 * Checks whether the given java persistent type has an id class annotation with the
	 * given fully qualified name.
	 * @param jpt - the java persistent type to be checked
	 * @param fqn - the fully qualified name of the id class
	 * @return true if the java persistent type has an id class annotation with the given
	 * fully qualified name, false otherwise.
	 */
	private boolean hasSameIdClass(PersistentType jpt, String fqn){
		JpaArtifactFactory jpaFactory = JpaArtifactFactory.instance();
        if(jpaFactory.hasIDClass(jpt) && jpaFactory.getIdType(jpt).equals(fqn)){
        	return true;
        }
        
        return false;
	}
	
	/**
	 * Checks whether the given java persistent type has an embedded id attribute of type,
	 * the given fully qualified name.
	 * @param jpt - the java persistent type to be checked
	 * @param fqn - the fully qualified name of the embedded id attribute's type
	 * @return true, if the java persistent type has an embedded id attribute with the
	 * given fully qualified name.
	 */
	private boolean hasSameEmbeddedId(PersistentType jpt, String fqn){
		JpaArtifactFactory jpaFactory = JpaArtifactFactory.instance();
		for(PersistentAttribute jpa : jpt.getAttributes()){
			if(jpaFactory.isEmbeddedId(jpa) && JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa).equals(fqn)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks whether the attribute which is going to be deleted is mapped as derived id.
	 * If yes, finds the helper attribute/field in the composite primary key class and if
	 * it is not used by another entity, deletes it too.
	 * @param attrName - the name of the attribute which is going to be deleted
	 * @param jpt - the java persistent type from which the attribute is originally deleted
	 */
	private void deleteFieldFromCompositePKClass(String attrName,
			PersistentType jpt) {
		PersistentAttribute jpa = jpt.getAttributeNamed(attrName);
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(jpa);
		if(attributeMapping instanceof SingleRelationshipMapping2_0){
			DerivedIdentity2_0 derivedIdentity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
			if(derivedIdentity.usesIdDerivedIdentityStrategy()){
				deleteFieldFromIdClassCompositePK(attrName, jpt);
			} else if(derivedIdentity.usesMapsIdDerivedIdentityStrategy()){
				deleteFieldFromEmbeddedIDCompositePK(jpt, jpa);
			}
		}
	}

	/**
	 * By the maps id annotation value, find the attribute which has to be deleted from the
	 * embeddable class and deletes it.
	 * @param jpt - the java persistent type from which the attribute is originally deleted
	 * @param jpa - the attribute to be deleted
	 */
	private void deleteFieldFromEmbeddedIDCompositePK(PersistentType jpt,
			PersistentAttribute jpa) {
		
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(jpa);
		if(attributeMapping instanceof SingleRelationshipMapping2_0){
			DerivedIdentity2_0 derivedIdentity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
			if(derivedIdentity.usesMapsIdDerivedIdentityStrategy()){
				String attribName = derivedIdentity.getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName();
				if(attribName == null)
					return;
				JpaArtifactFactory jpaFactory = JpaArtifactFactory.instance();
				for(PersistentAttribute jpa1 : jpt.getAttributes()){
					if(jpaFactory.isEmbeddedId(jpa1)){
						String fqn = JPAEditorUtil.getAttributeTypeNameWithGenerics(jpa1);
						if(isDeleteAttributeAllowed(jpt, fqn)){
							PersistentType embeddedJPT = jpaFactory.getContextPersistentType(jpt.getJpaProject(), fqn);
							if(embeddedJPT != null)
								jpaFactory.deleteAttribute(embeddedJPT, attribName, getFeatureProvider());
						}
					}
				}
			}
		}
	}

	/**
	 * Deletes the field with the given name from the id class.
	 * @param attrName - the name of the field to be deleted
	 * @param jpt - the java persistent type from which the attribute is originally deleted
	 */
	private void deleteFieldFromIdClassCompositePK(String attrName,
			PersistentType jpt) {
		JpaArtifactFactory jpaFactory = JpaArtifactFactory.instance();
		String idClassFQN = jpaFactory.getIdType(jpt);
		if(idClassFQN != null && isDeleteAttributeAllowed(jpt, idClassFQN)){
			IType type = jpaFactory.getType(jpt.getJpaProject().getJavaProject(), idClassFQN);
			Command deleteAttributeCommand = new DeleteAttributeCommand(type.getCompilationUnit(), null, attrName, getFeatureProvider());
			try {
				getJpaProjectManager().execute(deleteAttributeCommand, SynchronousUiCommandContext.instance());
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Cannot delete attribute with name " + attrName, e); //$NON-NLS-1$		
			}
		}
	}
	
	/**
	 * Finds all java persistent types, which either has attribute, mapped as embedded id and
	 * type, the given fully qualified name, or has an id class with the given fully qualified name.
	 * Then for each one java persistent type, checks whether it has an derived id. If at least one
	 * java persistent type has an derived id, then the method returns false. The method return true,
	 * if there isn't any java persistent type that has an derived identifier.
	 * @param jpt - the java persistent type, from which the attribute is originally deleted
	 * @param fqn - the fully qualified name of the composite primary key class
	 * @return true, if there isn't any java persistent type, that has a composite primary key class
	 * with the given fully qualified name and an derived identifiers; false - if there is at least
	 * one java persistent type with composite primary class, that has an derived id.
	 */
	private boolean isDeleteAttributeAllowed(PersistentType jpt, String fqn){
		Set<PersistentType> jpts = getAllJPTWithSameIDClassOrEmbeddedId(jpt, fqn);
		for(PersistentType perType : jpts){
			for(PersistentAttribute jpa : perType.getAttributes()){
				AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(jpa);
				if(attributeMapping instanceof SingleRelationshipMapping2_0){
					DerivedIdentity2_0 derivedIdentity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
					if(derivedIdentity.usesIdDerivedIdentityStrategy() || derivedIdentity.usesMapsIdDerivedIdentityStrategy()){
						return false;
					}
				}

			}
		}
		return true;
	}
	
	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}
}
