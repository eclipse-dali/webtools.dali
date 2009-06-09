/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.EmptyTagBooleanTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class MappedSuperclassTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public MappedSuperclassTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
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
	
	protected Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getXmlTypeMapping_ClassName(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlAccessHolder_Access(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getXmlTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getXmlTypeMapping_Description());
	}
	
	protected Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ORM_PKG.getXmlMappedSuperclass_IdClass());
	}
	
	protected Translator createExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_LISTENERS, ORM_PKG.getXmlMappedSuperclass_ExcludeDefaultListeners());
	}
	
	protected Translator createExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_SUPERCLASS_LISTENERS, ORM_PKG.getXmlMappedSuperclass_ExcludeSuperclassListeners());
	}
	
	protected Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getXmlMappedSuperclass_EntityListeners());
	}
	
	protected Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getXmlMappedSuperclass_PrePersist());
	}
	
	protected Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getXmlMappedSuperclass_PostPersist());
	}
	
	protected Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getXmlMappedSuperclass_PreRemove());
	}
	
	protected Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getXmlMappedSuperclass_PostRemove());
	}
	
	protected Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getXmlMappedSuperclass_PreUpdate());
	}
	
	protected Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getXmlMappedSuperclass_PostUpdate());
	}
	
	protected Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getXmlMappedSuperclass_PostLoad());
	}
	
	protected Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getXmlTypeMapping_Attributes());
	}
}
