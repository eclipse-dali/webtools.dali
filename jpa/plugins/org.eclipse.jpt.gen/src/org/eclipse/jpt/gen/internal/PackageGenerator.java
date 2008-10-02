/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.util.Iterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This generator will generate a package of entities for a set of tables.
 */
public class PackageGenerator {
	private final Config config;
	private final EntityGenerator.Config entityConfig;
	private final GenScope scope;


	// ********** public API **********

	public static void generateEntities(
			Config config,
			EntityGenerator.Config entityConfig,
			IProgressMonitor progressMonitor
	) {
		if ((config == null) || (entityConfig == null)) {
			throw new NullPointerException();
		}
		SubMonitor sm = SubMonitor.convert(progressMonitor, JptGenMessages.PackageGenerator_taskName, 100);
		new PackageGenerator(config, entityConfig, sm.newChild(10)).generateEntities(sm.newChild(90));
	}


	// ********** construction/initialization **********

	private PackageGenerator(
			Config config,
			EntityGenerator.Config entityConfig,
			IProgressMonitor progressMonitor
	) {
		super();
		this.config = config;
		this.entityConfig = entityConfig;
		this.scope = new GenScope(entityConfig, progressMonitor);
	}


	// ********** generation **********

	private void generateEntities(IProgressMonitor progressMonitor) {
		SubMonitor sm = SubMonitor.convert(progressMonitor, this.scope.entityTablesSize());
		for (Iterator<GenTable> stream = this.scope.entityGenTables(); stream.hasNext(); ) {
			this.checkCanceled(sm);
			this.generateEntity(stream.next(), sm.newChild(1));
		}
	}

	private void generateEntity(GenTable genTable, IProgressMonitor progressMonitor) {
		EntityGenerator.generateEntity(
				this.entityConfig,
				this.config.getPackageFragment(),
				genTable,
				progressMonitor
		);
	}

	private void checkCanceled(IProgressMonitor progressMonitor) {
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.scope);
	}


	// ********** config **********

	public static class Config {
		private IPackageFragment packageFragment;

		public IPackageFragment getPackageFragment() {
			return this.packageFragment;
		}

		public void setPackageFragment(IPackageFragment packageFragment) {
			this.packageFragment = packageFragment;
		}

	}

}
