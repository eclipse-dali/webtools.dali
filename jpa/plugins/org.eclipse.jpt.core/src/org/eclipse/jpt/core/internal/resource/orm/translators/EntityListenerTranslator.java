package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityListenerTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public EntityListenerTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	private Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createClassTranslator(),
			createPrePersistTranslator(),
			createPostPersistTranslator(),
			createPreRemoveTranslator(),
			createPostRemoveTranslator(),
			createPreUpdateTranslator(),
			createPostUpdateTranslator(),
			createPostLoadTranslator()
		};
	}
	
	private Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getEntityListener_ClassName());
	}
	
	private Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getEntityListener_PrePersist());
	}
	
	private Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getEntityListener_PostPersist());
	}
	
	private Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getEntityListener_PreRemove());
	}
	
	private Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getEntityListener_PostRemove());
	}
	
	private Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getEntityListener_PreUpdate());
	}
	
	private Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getEntityListener_PostUpdate());
	}
	
	private Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getEntityListener_PostLoad());
	}
}
