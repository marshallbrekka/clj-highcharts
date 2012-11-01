package org.marshallbrekka.highcharts.server.export;

import org.one2team.highcharts.server.export.Renderer;
import org.one2team.highcharts.server.export.Renderer.PojoRenderer;

public class SVGBaseRenderer<T> extends PojoRenderer<T> {

    @Override
    public void render() {
        wrapped.setOutputStream (getOutputStream()).render();
    }

    @Override
    public Renderer<T> setGlobalOptions (T options) {
        wrapped.setGlobalOptions (options);
        return this;
    }

    @Override
    public Renderer<T> setChartOptions (T options) {
        wrapped.setChartOptions (options);
        return this;
    }


    public SVGBaseRenderer (Renderer<T> wrapped) {
        this.wrapped = wrapped;
    }

    private final Renderer<T> wrapped;

}
