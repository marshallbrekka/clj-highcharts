package org.marshallbrekka.highcharts.server.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.one2team.highcharts.server.export.Renderer;
import org.one2team.highcharts.server.export.SVGRenderer;
import org.one2team.highcharts.server.export.util.SVGRendererInternal;

public class HighchartsSVGExporter<T> {


    public HighchartsSVGExporter (SVGRendererInternal<T> internalRenderer) {
        this.renderer = new SVGBaseRenderer<T> (new SVGRenderer<T> (internalRenderer));
    }

    public void export (T chartOptions,
                            T globalOptions,
                            File file) {

        OutputStream fos = null;
        try {
            fos = render (chartOptions, globalOptions, new FileOutputStream(file));

        } catch (Exception e) {
            e.printStackTrace ();
            throw (new RuntimeException (e));
        } finally {
            if (fos != null)
                IOUtils.closeQuietly (fos);
        }
    }

    public OutputStream render (T chartOptions,
                                     T globalOptions,
                                     OutputStream os) throws FileNotFoundException {
        renderer.setChartOptions (chartOptions)
                    .setGlobalOptions (globalOptions)
                    .setOutputStream (os)
                    .render ();
        return os;
    }

    public SVGBaseRenderer<T> getRenderer () {
        return renderer;
    }


    private final SVGBaseRenderer<T> renderer;


}
