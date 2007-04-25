package org.eclipse.jpt.ui.internal;

import java.util.Collection;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;

public interface IJpaPlatformUi
{
	Collection<IJpaStructureProvider> structureProviders();
	
	Collection<IJpaDetailsProvider> detailsProviders();
	
}
