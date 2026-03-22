/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;

/**
 * A {@link org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter}
 * that transparently handles both {@code javax.persistence} and
 * {@code jakarta.persistence} annotation names without requiring changes to
 * every individual {@code Source*Annotation} class.
 *
 * <h3>READ (find/synchronize)</h3>
 * <p>
 * {@link #getAnnotation(ModifiedDeclaration)} tries the <em>primary</em> name
 * first; if not found, it falls back to the <em>alias</em> name (the
 * corresponding name in the other namespace).  This means a javax-primary
 * adapter silently recognises {@code @jakarta.persistence.X} in JPA&nbsp;3.x
 * source files, and vice-versa.
 *
 * <h3>WRITE (add/remove)</h3>
 * <p>
 * {@code newMarkerAnnotation}, {@code newNormalAnnotation} and
 * {@code newSingleMemberAnnotation} always use the <em>primary</em> name.
 * {@link #removeAnnotation(ModifiedDeclaration)} removes whichever name is
 * present.
 *
 * <h3>Usage</h3>
 * <pre>
 * // JPA 2.x source annotations — drop-in replacement for
 * // {@code new SimpleDeclarationAnnotationAdapter("javax.persistence.X")}
 * // Reads both javax and jakarta; writes javax.
 * static final DAA = JakartaAwareDeclarationAnnotationAdapter.forJavax(JPA.BASIC);
 *
 * // JPA 3.x annotation definitions — reads both, writes jakarta.
 * static final DAA = JakartaAwareDeclarationAnnotationAdapter.forJakarta(JPA3_0.BASIC);
 * </pre>
 *
 * <h3>Extending to future namespaces</h3>
 * <p>
 * If a future JPA version introduces yet another namespace prefix, add a new
 * {@code forXxx} factory method here and add its alias to
 * {@link #deriveAlias(String)}.  No other files need changing.
 *
 * @see SimpleDeclarationAnnotationAdapter
 */
public final class JakartaAwareDeclarationAnnotationAdapter
	extends SimpleDeclarationAnnotationAdapter
{
	/** Javax persistence package prefix. */
	private static final String JAVAX  = "javax.persistence";  //$NON-NLS-1$

	/** Jakarta persistence package prefix. */
	private static final String JAKARTA = "jakarta.persistence"; //$NON-NLS-1$

	/**
	 * The cross-namespace alias used only for READ fall-back; may be
	 * {@code null} if the primary name is not in a known JPA namespace.
	 */
	private final String aliasAnnotationName;


	// ---- factory methods ------------------------------------------------

	/**
	 * Returns an adapter whose primary name is {@code javaxFqn} (used for
	 * writing) and whose alias is the corresponding
	 * {@code jakarta.persistence} name (used as a READ fall-back).
	 * <p>
	 * Drop-in replacement for
	 * {@code new SimpleDeclarationAnnotationAdapter("javax.persistence.X")}.
	 */
	public static JakartaAwareDeclarationAnnotationAdapter forJavax(String javaxFqn) {
		return new JakartaAwareDeclarationAnnotationAdapter(javaxFqn, deriveAlias(javaxFqn));
	}

	/**
	 * Returns an adapter whose primary name is {@code jakartaFqn} (used for
	 * writing) and whose alias is the corresponding
	 * {@code javax.persistence} name (used as a READ fall-back for migrated
	 * code).
	 */
	public static JakartaAwareDeclarationAnnotationAdapter forJakarta(String jakartaFqn) {
		return new JakartaAwareDeclarationAnnotationAdapter(jakartaFqn, deriveAlias(jakartaFqn));
	}

	/**
	 * Derives the cross-namespace alias for {@code name}.
	 * <ul>
	 *   <li>{@code javax.persistence.X} → {@code jakarta.persistence.X}</li>
	 *   <li>{@code jakarta.persistence.X} → {@code javax.persistence.X}</li>
	 *   <li>anything else → {@code null} (no alias)</li>
	 * </ul>
	 */
	private static String deriveAlias(String name) {
		if (name == null) {
			return null;
		}
		if (name.startsWith(JAVAX)) {
			return JAKARTA + name.substring(JAVAX.length());
		}
		if (name.startsWith(JAKARTA)) {
			return JAVAX + name.substring(JAKARTA.length());
		}
		return null;
	}

	private JakartaAwareDeclarationAnnotationAdapter(String primaryFqn, String aliasFqn) {
		super(primaryFqn);
		this.aliasAnnotationName = aliasFqn;
	}


	// ---- READ: try primary, fall back to alias --------------------------

	/**
	 * Returns the annotation matching the primary name, or — if absent — the
	 * annotation matching the alias name.
	 */
	@Override
	public Annotation getAnnotation(ModifiedDeclaration declaration) {
		Annotation annotation = super.getAnnotation(declaration);
		if (annotation == null && this.aliasAnnotationName != null) {
			annotation = declaration.getAnnotationNamed(this.aliasAnnotationName);
		}
		return annotation;
	}

	/**
	 * Returns the AST node for the annotation (either primary or alias), or
	 * the declaration itself if neither is present.
	 */
	@Override
	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		Annotation annotation = this.getAnnotation(declaration);
		return (annotation != null) ? annotation : declaration.getDeclaration();
	}


	// ---- WRITE: namespace-preserving add/replace -----------------------

	/**
	 * Namespace-preserving write: if the alias annotation is already present
	 * in the declaration (e.g. {@code @jakarta.persistence.X} when our primary
	 * is {@code javax.persistence.X}), we write back under the alias namespace
	 * so that existing source files are not silently migrated to the wrong
	 * package.
	 * <p>
	 * Concretely this means:<ul>
	 * <li>Modifying an element on {@code @jakarta.persistence.Basic} in a JPA
	 *     3.x file correctly produces {@code @jakarta.persistence.Basic(fetch=LAZY)}
	 *     instead of a spurious {@code @javax.persistence.Basic(fetch=LAZY)}.
	 * </ul>
	 * When no alias annotation is present the call falls through to the
	 * superclass which uses the primary name as normal.
	 */
	@Override
	protected void addAnnotationAndImport(ModifiedDeclaration declaration, Annotation annotation) {
		if (this.aliasAnnotationName != null
				&& declaration.getAnnotationNamed(this.aliasAnnotationName) != null) {
			// The alias variant is already in the file — keep the same namespace.
			declaration.addImport(this.aliasAnnotationName);
			declaration.replaceAnnotationNamed(this.aliasAnnotationName, annotation);
		} else {
			super.addAnnotationAndImport(declaration, annotation);
		}
	}


	// ---- REMOVE: remove whichever variant is present --------------------

	/**
	 * Removes the annotation identified by the primary name, then also
	 * removes any lingering annotation identified by the alias name.  This
	 * correctly handles source files that were partially migrated.
	 */
	@Override
	public void removeAnnotation(ModifiedDeclaration declaration) {
		super.removeAnnotation(declaration);   // primary
		if (this.aliasAnnotationName != null) {
			declaration.removeAnnotationNamed(this.aliasAnnotationName);  // alias
		}
	}
}
