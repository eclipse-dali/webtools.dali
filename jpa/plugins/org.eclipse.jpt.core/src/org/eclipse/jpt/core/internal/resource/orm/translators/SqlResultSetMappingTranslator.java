package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class SqlResultSetMappingTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public SqlResultSetMappingTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createNameTranslator(),
			createEntityResultTranslator(),
			createColumnResultTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getSqlResultSetMapping_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createEntityResultTranslator() {
		return new EntityResultTranslator(ENTITY_RESULT, ORM_PKG.getSqlResultSetMapping_EntityResults());
	}
	
	private Translator createColumnResultTranslator() {
		return new ColumnResultTranslator(COLUMN_RESULT, ORM_PKG.getSqlResultSetMapping_ColumnResults());
	}
}
