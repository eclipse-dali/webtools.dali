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
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
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
		return new Translator(CLASS, ORM_PKG.getMappedSuperclass_ClassName(), DOM_ATTRIBUTE);
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getMappedSuperclass_Access(), DOM_ATTRIBUTE);
	}
	
	private Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getMappedSuperclass_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getMappedSuperclass_Description());
	}
	
	private Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ORM_PKG.getMappedSuperclass_IdClass());
	}
	
	private Translator createExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_LISTENERS, ORM_PKG.getMappedSuperclass_ExcludeDefaultListeners());
	}
	
	private Translator createExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_SUPERCLASS_LISTENERS, ORM_PKG.getMappedSuperclass_ExcludeSuperclassListeners());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getMappedSuperclass_EntityListeners());
	}
	
	private Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getMappedSuperclass_PrePersist());
	}
	
	private Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getMappedSuperclass_PostPersist());
	}
	
	private Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getMappedSuperclass_PreRemove());
	}
	
	private Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getMappedSuperclass_PostRemove());
	}
	
	private Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getMappedSuperclass_PreUpdate());
	}
	
	private Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getMappedSuperclass_PostUpdate());
	}
	
	private Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getMappedSuperclass_PostLoad());
	}
	
	private Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getMappedSuperclass_Attributes());
	}
}
