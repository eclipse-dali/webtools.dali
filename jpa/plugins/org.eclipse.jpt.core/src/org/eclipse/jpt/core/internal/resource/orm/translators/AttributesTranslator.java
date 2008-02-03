package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class AttributesTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public AttributesTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createIdTranslator(),
			createEmbeddedIdTranslator(),
			createBasicTranslator(),
			createVersionTranslator(),
			createManyToOneTranslator(),
			createOneToManyTranslator(),
			createOneToOneTranslator(),
			createManyToManyTranslator(),
			createEmbeddedTranslator(),
			createTransientTranslator()
		};
	}
	
	private Translator createIdTranslator() {
		return new IdTranslator(ID, ORM_PKG.getAttributes_Ids());
	}
	
	private Translator createEmbeddedIdTranslator() {
		return new EmbeddedIdTranslator(EMBEDDED_ID, ORM_PKG.getAttributes_EmbeddedIds());
	}
	
	private Translator createBasicTranslator() {
		return new BasicTranslator(BASIC, ORM_PKG.getAttributes_Basics());
	}
	
	private Translator createVersionTranslator() {
		return new VersionTranslator(VERSION, ORM_PKG.getAttributes_Versions());
	}
	
	private Translator createManyToOneTranslator() {
		return new ManyToOneTranslator(MANY_TO_ONE, ORM_PKG.getAttributes_ManyToOnes());
	}
	
	private Translator createOneToManyTranslator() {
		return new OneToManyTranslator(ONE_TO_MANY, ORM_PKG.getAttributes_OneToManys());
	}
	
	private Translator createOneToOneTranslator() {
		return new OneToOneTranslator(ONE_TO_ONE, ORM_PKG.getAttributes_OneToOnes());
	}
	
	private Translator createManyToManyTranslator() {
		return new ManyToManyTranslator(MANY_TO_MANY, ORM_PKG.getAttributes_ManyToManys());
	}
	
	private Translator createEmbeddedTranslator() {
		return new EmbeddedTranslator(EMBEDDED, ORM_PKG.getAttributes_Embeddeds());
	}
	
	private Translator createTransientTranslator() {
		return new TransientTranslator(TRANSIENT, ORM_PKG.getAttributes_Transients());
	}
}
