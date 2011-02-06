/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2;

import java.io.Serializable;

/**
 * JPA 2.0 Canonical Metamodel synchronizer.
 * <p>
 * Notes:
 * <ul><li>
 * When a JPA project is first created (e.g. when the user adds the JPA
 * Facet to a Faceted project or when a workspace containing a JPA project is
 * opened), if it is configured to generate the
 * Canonical Metamodel (i.e. its metamodel source folder is non-null), it will
 * first discover what metamodel classes are already present in the metamodel
 * source folder. Any class appropriately annotated with
 * <code>javax.persistence.metamodel.StaticMetamodel</code>
 * and <code>javax.annotation.Generated</code>
 * will be added to the Canonical Metamodel
 * (see {@link org.eclipse.jpt.jpa.core.internal.resource.java.source.SourcePersistentType#isGeneratedMetamodel()}).
 * Once the JPA project's context model is constructed, a new Canonical
 * Metamodel is generated and merged with the classes already present in the
 * metamodel source folder.
 * </li><li>
 * When a JPA project's metamodel source folder setting is cleared, the Canonical
 * Metamodel is cleared from the context model, but the generated source files are
 * left in place.
 * </li><li>
 * When a JPA project's metamodel source folder is set to a non-null value,
 * like when a JPA project is first created, the resulting Canonical Metamodel
 * will be merged with whatever is already present in the folder.
 * </li></ul>
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
public interface MetamodelSynchronizer {

	void initializeMetamodel();

	void synchronizeMetamodel();

	void disposeMetamodel();


	/**
	 * Singleton implemetation of the metamodel synchronizer interface that
	 * does nothing. (Not sure we need this....)
	 */
	final class Null implements MetamodelSynchronizer, Serializable {
		public static final MetamodelSynchronizer INSTANCE = new Null();
		public static MetamodelSynchronizer instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void initializeMetamodel() {
			// do nothing
		}
		public void synchronizeMetamodel() {
			// do nothing
		}
		public void disposeMetamodel() {
			// do nothing
		}
		@Override
		public String toString() {
			return "MetamodelSynchronizer.Null";  //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
