package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityListenersTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public EntityListenersTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createEntityListenerTranslator()
		};
	}
	
	private Translator createEntityListenerTranslator() {
		return new EntityListenerTranslator(ENTITY_LISTENER, ORM_PKG.getEntityListeners_EntityListeners());
	}
}
