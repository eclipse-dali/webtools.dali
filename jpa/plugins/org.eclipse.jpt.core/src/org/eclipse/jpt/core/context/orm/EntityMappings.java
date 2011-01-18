/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.persistence.PersistentTypeContainer;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Context model corresponding to the
 * XML resource model {@link XmlEntityMappings},
 * which corresponds to the <code>entity-mappings</code> element
 * in the <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
  * 
 * @version 3.0
 * @since 2.0
*/
public interface EntityMappings
	extends MappingFileRoot, PersistentType.Owner, PersistentTypeContainer
{
	/**
	 * Covariant override.
	 */
	OrmXml getParent();

	XmlEntityMappings getXmlEntityMappings();

	String getVersion();

	String getDescription();
	void setDescription(String description);
		String DESCRIPTION_PROPERTY = "description"; //$NON-NLS-1$

	/**
	 * "The <code>package</code> subelement specifies the package of the
	 * classes listed within the subelements and attributes of the same mapping
	 * file only. The <code>package</code> subelement is overridden if the fully
	 * qualified class name is specified for a class and the two disagree."
	 * <p>
	 * <strong>NB:</strong> No mention of how to resolve duplicates in the
	 * "default" package or sub-packages:<ul>
	 * <li><code>Bar</code> (in "default" package) vs. <code>foo.Bar</code>
	 * <li><code>baz.Bar</code> vs. <code>foo.baz.Bar</code>
	 * </ul>
	 * when package is specified as <code>foo</code>.
	 */
	String getPackage();
	void setPackage(String package_);
		String PACKAGE_PROPERTY = "package"; //$NON-NLS-1$

	AccessType getSpecifiedAccess();
	void setSpecifiedAccess(AccessType access);
		String SPECIFIED_ACCESS_PROPERTY = "specifiedAccess"; //$NON-NLS-1$
	AccessType getDefaultAccess();
		String DEFAULT_ACCESS_PROPERTY = "defaultAccess"; //$NON-NLS-1$

	/**
	 * Return the database schema container, which can be either a catalog or,
	 * if the database does not support catalogs, the database itself.
	 */
	SchemaContainer getDbSchemaContainer();

	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String catalog);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$
	Catalog getDbCatalog();

	String getSpecifiedSchema();
	void setSpecifiedSchema(String schema);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$
	Schema getDbSchema();

	/**
	 * Covariant override.
	 */
	OrmPersistenceUnitMetadata getPersistenceUnitMetadata();

	/**
	 * Covariant override.
	 */
	ListIterable<OrmPersistentType> getPersistentTypes();
	int getPersistentTypesSize();
	OrmPersistentType addPersistentType(String mappingKey, String className);
	void removePersistentType(int index);
	void removePersistentType(OrmPersistentType persistentType);
	//void movePersistentType(int targetIndex, int sourceIndex);
	boolean containsPersistentType(String className);
	/**
	 * Return the persistent type listed in the mapping file
	 * with the specified type name. Return null if none exists.
	 */
	OrmPersistentType getPersistentType(String className);
		String PERSISTENT_TYPES_LIST = "persistentTypes"; //$NON-NLS-1$

	ListIterable<OrmSequenceGenerator> getSequenceGenerators();
	int getSequenceGeneratorsSize();
	OrmSequenceGenerator addSequenceGenerator();
	OrmSequenceGenerator addSequenceGenerator(int index);
	void removeSequenceGenerator(int index);
	void removeSequenceGenerator(OrmSequenceGenerator sequenceGenerator);
	void moveSequenceGenerator(int targetIndex, int sourceIndex);
		String SEQUENCE_GENERATORS_LIST = "sequenceGenerators"; //$NON-NLS-1$

	ListIterable<OrmTableGenerator> getTableGenerators();
	int getTableGeneratorsSize();
	OrmTableGenerator addTableGenerator();
	OrmTableGenerator addTableGenerator(int index);
	void removeTableGenerator(int index);
	void removeTableGenerator(OrmTableGenerator tableGenerator);
	void moveTableGenerator(int targetIndex, int sourceIndex);
		String TABLE_GENERATORS_LIST = "tableGenerators"; //$NON-NLS-1$

	OrmQueryContainer getQueryContainer();

	/**
	 * Return the default package to be used for persistent types in this
	 * context.
	 */
	String getDefaultPersistentTypePackage();

	void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping);

	boolean containsOffset(int textOffset);

	/**
	 * Return the Java resource persistent type for the specified class name
	 * found in the JPA project. First look for one with the specified
	 * name (since it might be fully qualified). If not found, prepend the
	 * default package name and try again.
	 * 
	 * @see #getPackage()
	 */
	JavaResourcePersistentType resolveJavaResourcePersistentType(String className);

	/**
	 * Return the persistent type for the specified class name
	 * found in the persistence unit. First look for one with the specified
	 * name (since it might be fully qualified). If not found, prepend the
	 * default package name and try again.
	 * 
	 * @see #getPackage()
	 */
	PersistentType resolvePersistentType(String className);



	// ********** refactoring **********

	/**
	 * Create DeleteEdits for deleting references (if any) to the type about to be deleted.
	 * Return an EmptyIterable if there are not any references to the given type.
	 */
	Iterable<DeleteEdit> createDeleteTypeEdits(IType type);

	/**
	 * Create ReplaceEdits for renaming any references to the originalType to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	/**
	 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
	 * The originalType has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create ReplaceEdits for renaming any references to the originalPackage to the newName.
	 * The originalPackage has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);
}
