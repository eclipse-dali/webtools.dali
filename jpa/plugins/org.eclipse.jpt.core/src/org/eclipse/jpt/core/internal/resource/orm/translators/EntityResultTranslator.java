package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityResultTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public EntityResultTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createEntityClassTranslator(),
			createDiscriminatorColumnTranslator(),
			createFieldResultTranslator()
		};
	}
	
	private Translator createEntityClassTranslator() {
		return new Translator(ENTITY_CLASS, ORM_PKG.getEntityResult_EntityClass(), DOM_ATTRIBUTE);
	}
	
	private Translator createDiscriminatorColumnTranslator() {
		return new Translator(DISCRIMINATOR_COLUMN, ORM_PKG.getEntityResult_DiscriminatorColumn(), DOM_ATTRIBUTE);
	}
	
	private Translator createFieldResultTranslator() {
		return new FieldResultTranslator(FIELD_RESULT, ORM_PKG.getEntityResult_FieldResults());
	}
}
