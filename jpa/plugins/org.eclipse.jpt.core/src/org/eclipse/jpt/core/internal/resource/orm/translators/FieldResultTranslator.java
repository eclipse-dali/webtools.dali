package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class FieldResultTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public FieldResultTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
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
			createNameTranslator(),
			createColumnTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getFieldResult_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnTranslator() {
		return new Translator(COLUMN, ORM_PKG.getFieldResult_Column(), DOM_ATTRIBUTE);
	}
}
