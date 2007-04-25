package org.eclipse.jpt.core.internal.content.persistence.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.persistence.PersistencePackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class MappingFileTranslator extends Translator
{
	private static Translator[] children;
	
	
	public MappingFileTranslator(String domNameAndPath, EStructuralFeature feature, int style) {
		super(domNameAndPath, feature, style);
	}

	public MappingFileTranslator(String domNameAndPath, EStructuralFeature feature) {
		super(domNameAndPath, feature);
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private static Translator[] createChildren() {
		return new Translator[] {
			new Translator(TEXT_ATTRIBUTE_VALUE, PersistencePackage.eINSTANCE.getMappingFileRef_FileName())
		};
	}
}
