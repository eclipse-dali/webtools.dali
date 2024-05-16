package org.eclipse.jpt.jpa.core.jpa3_1;

import org.eclipse.jpt.jpa.core.jpa3_0.JpaProject3_0;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JPA 3.1 project.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 */
public interface JpaProject3_1 extends JpaProject3_0 {
	// ********** JPA facet **********

	/**
	 * The JPA 3.1 project facet version string.
	 * <p>
	 * Value: {@value}
	 */
	String FACET_VERSION_STRING = "3.1"; //$NON-NLS-1$

	/**
	 * The JPA 3.0 project facet version.
	 */
	IProjectFacetVersion FACET_VERSION = FACET.getVersion(FACET_VERSION_STRING);

}
