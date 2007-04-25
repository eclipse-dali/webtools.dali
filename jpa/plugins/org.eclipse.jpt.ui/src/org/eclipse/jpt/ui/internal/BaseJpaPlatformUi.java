package org.eclipse.jpt.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.structure.JavaStructureProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.jpt.ui.internal.xml.details.XmlDetailsProvider;
import org.eclipse.jpt.ui.internal.xml.structure.XmlStructureProvider;

public abstract class BaseJpaPlatformUi implements IJpaPlatformUi
{
	private Collection<IJpaDetailsProvider> detailsProviders;
	private Collection<IJpaStructureProvider> structureProviders;
	
	public Collection<IJpaDetailsProvider> detailsProviders() {
		if (this.detailsProviders == null) {
			this.detailsProviders = buildJpaDetailsProvider();
		}
		return this.detailsProviders;
	}

	protected Collection<IJpaDetailsProvider> buildJpaDetailsProvider() {
		Collection<IJpaDetailsProvider> detailsProviders = new ArrayList<IJpaDetailsProvider>();
		detailsProviders.add(new JavaDetailsProvider());
		detailsProviders.add(new XmlDetailsProvider());
		return detailsProviders;
	}
	
	public Collection<IJpaStructureProvider> structureProviders() {
		if (this.structureProviders == null) {
			this.structureProviders = buildJpaStructureProvider();
		}
		return this.structureProviders;
	}
	
	protected Collection<IJpaStructureProvider> buildJpaStructureProvider() {
		Collection<IJpaStructureProvider> structureProviders = new ArrayList<IJpaStructureProvider>();
		structureProviders.add(new JavaStructureProvider());
		structureProviders.add(new XmlStructureProvider());
		return structureProviders;
	}
	

}
