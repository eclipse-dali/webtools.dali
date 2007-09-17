package org.eclipse.jpt.core.internal.resource.persistence.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.BooleanTranslator;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceUnitTranslator extends Translator
	implements PersistenceXmlMapper
{
	private Translator[] children;
	
	
	public PersistenceUnitTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createTransactionTypeTranslator(),
			createDescriptionTranslator(),
			createProviderTranslator(),
			createJtaDataSourceTranslator(),
			createNonJtaDataSourceTranslator(),
			createMappingFileTranslator(),
			createJarFileTranslator(),
			createClassTranslator(),
			createExcludeUnlistedClassesTranslator(),
			createPropertiesTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, PERSISTENCE_PKG.getPersistenceUnit_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createTransactionTypeTranslator() {
		return new Translator(TRANSACTION_TYPE, PERSISTENCE_PKG.getPersistenceUnit_TransactionType(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, PERSISTENCE_PKG.getPersistenceUnit_Description());
	}
	
	private Translator createProviderTranslator() {
		return new Translator(PROVIDER, PERSISTENCE_PKG.getPersistenceUnit_Provider());
	}
	
	private Translator createJtaDataSourceTranslator() {
		return new Translator(JTA_DATA_SOURCE, PERSISTENCE_PKG.getPersistenceUnit_JtaDataSource());
	}
	
	private Translator createNonJtaDataSourceTranslator() {
		return new Translator(NON_JTA_DATA_SOURCE, PERSISTENCE_PKG.getPersistenceUnit_NonJtaDataSource());
	}
	
	private Translator createMappingFileTranslator() {
		return new MappingFileTranslator(MAPPING_FILE, PERSISTENCE_PKG.getPersistenceUnit_MappingFiles());
	}
	
	private Translator createJarFileTranslator() {
		return new Translator(JAR_FILE, PERSISTENCE_PKG.getPersistenceUnit_JarFiles());
	}
	
	private Translator createClassTranslator() {
		return new JavaClassRefTranslator(CLASS, PERSISTENCE_PKG.getPersistenceUnit_Classes());
	}
	
	private Translator createExcludeUnlistedClassesTranslator() {
		return new BooleanTranslator(EXCLUDE_UNLISTED_CLASSES, PERSISTENCE_PKG.getPersistenceUnit_ExcludeUnlistedClasses());
	}
	
	private Translator createPropertiesTranslator() {
		return new PropertiesTranslator(PROPERTIES, PERSISTENCE_PKG.getPersistenceUnit_Properties());
	}
}
