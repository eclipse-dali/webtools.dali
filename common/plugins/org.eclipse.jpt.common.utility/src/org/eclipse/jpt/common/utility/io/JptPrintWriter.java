/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.Writer;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Extend {@link PrintWriter} to give the option to select a line separator.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JptPrintWriter
	extends PrintWriter
{
    private String lineSeparator;
		
	public JptPrintWriter(Writer out) {
		this(out, StringTools.CR); // use system property by default
	}

	public JptPrintWriter(Writer out, String lineSeparator) {
		super(out);
		this.lineSeparator = lineSeparator;
	}

	@Override
	public void println() {
		try {
			synchronized (this.lock) {
				if (this.out == null) {
					throw new IOException("Stream closed"); //$NON-NLS-1$
				}
				this.out.write(this.lineSeparator);
			}
		}
		catch (InterruptedIOException e) {
			Thread.currentThread().interrupt();
		}
		catch (IOException e) {
			this.setError();
		}
	}
}
