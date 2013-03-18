/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will transform a possibly-null {@link Boolean} input into
 * a non-null {@link Boolean} output:<ul>
 * <li>When the original {@link Boolean} is <em>not</em> <code>null</code>,
 * the transformer will return it unchanged.
 * <li>When the original {@link Boolean} <em>is</em> <code>null</code>,
 * the transformer will return its client-configured "null value"
 * ({@link Boolean#TRUE} or {@link Boolean#FALSE}).
 * </ul>
 */
public final class BooleanTransformer
	implements Transformer<Boolean, Boolean>, Serializable
{
	// not null
	private final Boolean nullValue;

	/**
	 * A {@link Transformer} that will return the original {@link Boolean} when
	 * it is non-<code>null</code>; otherwise the {@link Transformer} will return
	 * {@link Boolean#TRUE}.
	 */
	public static final Transformer<Boolean, Boolean> TRUE = new BooleanTransformer(Boolean.TRUE);

	/**
	 * A {@link Transformer} that will return the original {@link Boolean} when
	 * it is non-<code>null</code>; otherwise the {@link Transformer} will return
	 * {@link Boolean#FALSE}.
	 */
	public static final Transformer<Boolean, Boolean> FALSE = new BooleanTransformer(Boolean.FALSE);

	/**
	 * Return a transformer that will return the specified value if the original
	 * value is <code>null</code>. Throw a {@link NullPointerException} if the
	 * specified value is <code>null</code>.
	 */
	public static Transformer<Boolean, Boolean> valueOf(Boolean nullValue) {
		return valueOf(nullValue.booleanValue());
	}

	/**
	 * Return a transformer that will return the {@link Boolean} corresponding
	 * to the specified value if the original value is <code>null</code>.
	 */
	public static Transformer<Boolean, Boolean> valueOf(boolean nullValue) {
		return nullValue ? TRUE : FALSE;
	}

	/**
	 * Ensure only 2 constant versions.
	 */
	private BooleanTransformer(Boolean nullValue) {
		super();
		if (nullValue == null) {
			throw new NullPointerException();
		}
		this.nullValue = nullValue;
	}

	public Boolean transform(Boolean b) {
		return (b != null) ? b : this.nullValue;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.nullValue);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the appropriate constant
		return valueOf(this.nullValue);
	}
}
