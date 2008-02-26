/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class MappedSuperclassTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public MappedSuperclassTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createClassTranslator(),
			createAccessTranslator(),
			createMetadataCompleteTranslator(),
			createDescriptionTranslator(),
			createIdClassTranslator(),
			createExcludeDefaultListenersTranslator(),
			createExcludeSuperclassListenersTranslator(),
			createEntityListenersTranslator(),
			createPrePersistTranslator(),
			createPostPersistTranslator(),
			createPreRemoveTranslator(),
			createPostRemoveTranslator(),
			createPreUpdateTranslator(),
			createPostUpdateTranslator(),
			createPostLoadTranslator(),
			createAttributesTranslator()
		};
	}
	
	private Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getAbstractTypeMapping_ClassName(), DOM_ATTRIBUTE);
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getAbstractTypeMapping_Access(), DOM_ATTRIBUTE);
	}
	
	private Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getAbstractTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getAbstractTypeMapping_Description());
	}
	
	private Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ORM_PKG.getXmlMappedSuperclass_IdClass());
	}
	
	private Translator createExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_LISTENERS, ORM_PKG.getXmlMappedSuperclass_ExcludeDefaultListeners());
	}
	
	private Translator createExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_SUPERCLASS_LISTENERS, ORM_PKG.getXmlMappedSuperclass_ExcludeSuperclassListeners());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getXmlMappedSuperclass_EntityListeners());
	}
	
	private Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getXmlMappedSuperclass_PrePersist());
	}
	
	private Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getXmlMappedSuperclass_PostPersist());
	}
	
	private Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getXmlMappedSuperclass_PreRemove());
	}
	
	private Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getXmlMappedSuperclass_PostRemove());
	}
	
	private Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getXmlMappedSuperclass_PreUpdate());
	}
	
	private Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getXmlMappedSuperclass_PostUpdate());
	}
	
	private Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getXmlMappedSuperclass_PostLoad());
	}
	
	private Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getAbstractTypeMapping_Attributes());
	}
}
