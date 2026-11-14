package pe.esan.diarioespacial.presentation.asteroid

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formato2(valor: Double): String =
    DecimalFormat(
        "#,##0.##",
        DecimalFormatSymbols(Locale.US)
    ).format(valor)