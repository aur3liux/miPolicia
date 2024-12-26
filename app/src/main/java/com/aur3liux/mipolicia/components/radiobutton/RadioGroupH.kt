package com.aur3liux.encuestaprofe.view.components.radiobutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RadioGroupH(
    modifier: Modifier,
    sColor: Color,
    uColor: Color,
    sizeF : TextUnit = 14.sp,
    sizeFS : TextUnit = 14.sp,
    w: Dp = 250.dp,
    vPadding: Dp = 1.dp,
    items: List<DataRadioButton>,
    selection: DataRadioButton,
    onItemClick: ((DataRadioButton) -> Unit)
) {
    Row(modifier = modifier.selectableGroup(),
        horizontalArrangement = Arrangement.Center) {
        items.forEach { item ->
            LabelledRadioButton(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .selectable(
                        selected = item == selection,
                        onClick = {
                            onItemClick(item)
                        },
                        role = Role.Switch),
                label = item.opcion,
                sizeF = sizeF,
                sizeFS = sizeFS,
                selected = item == selection,
                sColor = sColor,
                uColor =  uColor,
                w = w,
                vPadding = vPadding,
                onClick = {
                    onItemClick(item)
                }
            )
        }
    }
}