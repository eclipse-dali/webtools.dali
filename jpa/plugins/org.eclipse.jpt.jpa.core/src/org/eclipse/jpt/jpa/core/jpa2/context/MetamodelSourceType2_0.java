/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import com.ibm.icu.text.Collator;

/**
 * JPA 2.0 metamodel source type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface MetamodelSourceType2_0 {

	/**
	 * Return the source type's name.
	 */
	String getName();

	/**
	 * Return whether the source type is "managed" (i.e. persistent).
	 */
	boolean isManaged();

	/**
	 * Return the source type's super type.
	 */
	PersistentType getSuperPersistentType();

	/**
	 * Return the source type's attributes.
	 */
	ListIterable<? extends PersistentAttribute> getAttributes();

	/**
	 * Return the file generated as a result of the metamodel synchronization.
	 */
	IFile getMetamodelFile();

	/**
	 * Return the source type's JPA project.
	 */
	JpaProject getJpaProject();

	/**
	 * Synchronize the source type's metamodel, using the specified member type
	 * tree.
	 */
	void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree);

	/**
	 * Print the body of the source type's metamodel class on the specified
	 * writer, using the specified member type tree.
	 */
	void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree);


	/**
	 * {@link Comparator} that can be used to compare source types.
	 */
	Comparator<MetamodelSourceType2_0> COMPARATOR = new MetamodelSourceTypeComparator();
	class MetamodelSourceTypeComparator
		extends ComparatorAdapter<MetamodelSourceType2_0>
	{
		@Override
		public int compare(MetamodelSourceType2_0 type1, MetamodelSourceType2_0 type2) {
			return Collator.getInstance().compare(type1.getName(), type2.getName());
		}
	}


	/**
	 * This interface is used by the source type to synchronize the metamodel
	 * as required by changes to the context model.
	 */
	interface Synchronizer {
		/**
		 * Return the file generated as a result of the metamodel synchronization.
		 */
		IFile getFile();

		/**
		 * Synchronize the metamodel with the current state of the source
		 * type, using the specified member type tree.
		 */
		void synchronize(Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree);

		/**
		 * Print the body of the metamodel class on the specified writer,
		 * using the specified member type tree.
		 */
		void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree);
	}
}
