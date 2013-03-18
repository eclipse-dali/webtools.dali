/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentTypeContainer;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;

/**
 * Context model corresponding to the
 * <code>mapping-file</code> element
 * in the <code>persistence.xml</code> file.
 * <p>
 * <strong>NB:</strong>
 * While this context model corresponds to an XML resource model, the mapping
 * file it references does <em>not</em> necessarily correspond to an XML
 * resource model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface MappingFileRef
	extends JpaStructureNode, PersistentTypeContainer, MappingFileRefactoringParticipant, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	/**
	 * Covariant override.
	 */
	PersistenceUnit getParent();


	// ********** file name **********

	/**
	 * String constant associated with changes to the file name.
	 */
	String FILE_NAME_PROPERTY = "fileName"; //$NON-NLS-1$

	/**
	 * Return the file name of the mapping file ref.
	 */
	String getFileName();

	/**
	 * Set the file name of the mapping file ref.
	 */
	void setFileName(String fileName);


	// ********** mapping file (orm.xml) **********

	/**
	 * String constant associated with changes to the mapping file.
	 */
	String MAPPING_FILE_PROPERTY = "mappingFile"; //$NON-NLS-1$

	/**
	 * Return mapping file corresponding to the mapping file ref's file name.
	 * This can be <code>null</code> if the file name is invalid or if the
	 * file's content is unsupported.
	 */
	MappingFile getMappingFile();

	Transformer<MappingFileRef, MappingFile> MAPPING_FILE_TRANSFORMER = new MappingFileTransformer();
	class MappingFileTransformer
		extends TransformerAdapter<MappingFileRef, MappingFile>
	{
		@Override
		public MappingFile transform(MappingFileRef ref) {
			return ref.getMappingFile();
		}
	}


	// ********** persistence unit metadata **********

	/**
	 * Return the mapping file's persistence unit metadata.
	 */
	MappingFilePersistenceUnitMetadata getPersistenceUnitMetadata();

	/**
	 * Return whether the mapping file's persistence unit metadata exist.
	 */
	boolean persistenceUnitMetadataExists();

	Predicate<MappingFileRef> PERSISTENCE_UNIT_METADATA_EXISTS = new PersistenceUnitMetadataExists();
	class PersistenceUnitMetadataExists
		extends PredicateAdapter<MappingFileRef>
	{
		@Override
		public boolean evaluate(MappingFileRef mappingFileRef) {
			return mappingFileRef.persistenceUnitMetadataExists();
		}
	}


	// ********** misc **********

	/**
	 * Return the mapping file ref's corresponding XML mapping file ref.
	 */
	XmlMappingFileRef getXmlMappingFileRef();

	/**
	 * Return whether the mapping file ref is a
	 * <code>persistence.xml</code> default (as opposed to
	 * explicitly specified).
	 */
	boolean isDefault();

	/**
	 * @see MappingFile#getMappingFileQueries()
	 */
	Iterable<Query> getMappingFileQueries();

	Transformer<MappingFileRef, Iterable<Query>> MAPPING_FILE_QUERIES_TRANSFORMER = new MappingFileQueriesTransformer();
	class MappingFileQueriesTransformer
		extends TransformerAdapter<MappingFileRef, Iterable<Query>>
	{
		@Override
		public Iterable<Query> transform(MappingFileRef ref) {
			return ref.getMappingFileQueries();
		}
	}

	/**
	 * @see MappingFile#getMappingFileGenerators()
	 */
	Iterable<Generator> getMappingFileGenerators();

	Transformer<MappingFileRef, Iterable<Generator>> MAPPING_FILE_GENERATORS_TRANSFORMER = new MappingFileGeneratorsTransformer();
	class MappingFileGeneratorsTransformer
		extends TransformerAdapter<MappingFileRef, Iterable<Generator>>
	{
		@Override
		public Iterable<Generator> transform(MappingFileRef ref) {
			return ref.getMappingFileGenerators();
		}
	}

	//TODO would like to remove this eventually
	void dispose();
}
