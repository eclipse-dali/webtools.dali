/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2;

import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.db.DatabaseIdentifierAdapter;

/**
 * JPA 2.0 factory
 *<p> 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.jpa.core.internal.jpa2.GenericJpaFactory2_0
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JpaFactory2_0
	extends JpaFactory
{
	
	// ********** Core Model **********

	/**
	 * Return an identifier adapter that can be used to convert database
	 * identifiers to names and vice versa, respecting the <code>delimited-identifiers</code>
	 * in <code>persistence.xml</code>.
	 */
	DatabaseIdentifierAdapter buildDatabaseIdentifierAdapter(JpaDataSource dataSource);

	MetamodelSourceType2_0.Synchronizer buildMetamodelSynchronizer(MetamodelSourceType2_0 sourceType);

	
	// ********** Java Context Model **********
	
	JavaDerivedIdentity2_0 buildJavaDerivedIdentity(JavaSingleRelationshipMapping2_0 parent);
	
	JavaElementCollectionMapping2_0 buildJavaElementCollectionMapping(JavaSpecifiedPersistentAttribute parent);
	
	Cacheable2_0 buildJavaCacheable(JavaCacheableReference2_0 parent);
	
	OrphanRemovable2_0 buildJavaOrphanRemoval(OrphanRemovalMapping2_0 parent);
	
	JavaSpecifiedOrderColumn2_0 buildJavaOrderColumn(JavaSpecifiedOrderColumn2_0.ParentAdapter parentAdapter);
	
	JavaCollectionTable2_0 buildJavaCollectionTable(JavaCollectionTable2_0.ParentAdapter parentAdapter);

	JavaSpecifiedColumn buildJavaMapKeyColumn(JavaSpecifiedColumn.ParentAdapter parentAdapter);
	
	JavaOrderable2_0 buildJavaOrderable(JavaOrderable2_0.ParentAdapter parentAdapter);

	/**
	 * Use {@link #buildJavaOrderable(JavaOrderable2_0.ParentAdapter)}.
	 */
	Orderable buildJavaOrderable(JavaAttributeMapping parent);
}
