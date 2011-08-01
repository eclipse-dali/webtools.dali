/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2;

import java.io.Serializable;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.GeneratedAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.StaticMetamodelAnnotation;

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
 * (see {@link MetamodelTools#isGeneratedMetamodelTopLevelType(JavaResourceAbstractType, IPackageFragmentRoot)}).
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

	/**
	 * The value used to tag a generated type:
	 * <pre>
	 * &#64;javax.annotation.Generated(value="Dali", date="2009-11-23T13:56:06.171-0500")
	 * </pre>
	 */
	String METAMODEL_GENERATED_ANNOTATION_VALUE = "Dali"; //$NON-NLS-1$

	void initializeMetamodel();

	void synchronizeMetamodel();

	void disposeMetamodel();


	/**
	 * Singleton implementation of the metamodel synchronizer interface that
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

	final class MetamodelTools {

		/**
		 * The type must be:<ul>
		 * <li>in the specified source folder
		 * <li>a top-level type
		 * <li>annotated with <code>&#64;javax.annotation.Generated</code> with
		 *     the appropriate <code>value</code> and <code>date</code>
		 * <li>either itself or one of its nested types annotated with
		 *     <code>&#64;javax.persistence.metamodel.StaticMetamodel</code>
		 * </ul>
		 */
		public static boolean isGeneratedMetamodelTopLevelType(JavaResourceAbstractType jrat, IPackageFragmentRoot sourceFolder) {
			if ( ! jrat.isIn(sourceFolder)) {
				return false;
			}
			return isGeneratedMetamodelTopLevelType(jrat);
		}

		/**
		 * The type must be:<ul>
		 * <li>a top-level type
		 * <li>annotated with <code>&#64;javax.annotation.Generated</code> with
		 *     the appropriate <code>value</code> and <code>date</code>
		 * <li>either itself or one of its nested types annotated with
		 *     <code>&#64;javax.persistence.metamodel.StaticMetamodel</code>
		 * </ul>
		 */
		public static boolean isGeneratedMetamodelTopLevelType(JavaResourceAbstractType jrat) {
			if ( ! isGenerated(jrat)) {
				return false;
			}
			// if we get here we know we have a top-level type, since only top-level
			// types are annotated @Generated; now see if anything is a metamodel
			return isMetamodel(jrat);
		}

		/**
		 * The type must be annotated with
		 * <code>&#64;javax.annotation.Generated</code> with the appropriate
		 * <code>value</code> and <code>date</code>.
		 */
		public static boolean isGenerated(JavaResourceAbstractType jrat) {
			GeneratedAnnotation generatedAnnotation = (GeneratedAnnotation) jrat.getAnnotation(GeneratedAnnotation.ANNOTATION_NAME);
			if (generatedAnnotation == null) {
				return false;
			}
			if (generatedAnnotation.getValuesSize() != 1) {
				return false;
			}
			if ( ! generatedAnnotation.getValue(0).equals(METAMODEL_GENERATED_ANNOTATION_VALUE)) {
				return false;
			}
			if (StringTools.stringIsEmpty(generatedAnnotation.getDate())) {
				return false;
			}
			return true;
		}
		
		public static boolean isMetamodel(JavaResourceAbstractType jrat) {
			// if we get here we know we have a top-level type, since only top-level
			// types are annotated @Generated; now see if anything is a metamodel
			for (JavaResourceAbstractType type : jrat.getAllTypes()) {
				if (type.getAnnotation(StaticMetamodelAnnotation.ANNOTATION_NAME) != null) {
					return true;
				}
			}
			return false;
		}

	}
}
