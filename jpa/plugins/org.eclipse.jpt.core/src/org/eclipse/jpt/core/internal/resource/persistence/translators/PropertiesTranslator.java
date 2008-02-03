package org.eclipse.jpt.core.internal.resource.persistence.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PropertiesTranslator extends Translator
	implements PersistenceXmlMapper
{
	private Translator[] children;
	
	
	public PropertiesTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createPropertyTranslator()
		};
	}
	
	private Translator createPropertyTranslator() {
		return new PropertyTranslator(PROPERTY, PERSISTENCE_PKG.getXmlProperties_Properties());
	}
}
