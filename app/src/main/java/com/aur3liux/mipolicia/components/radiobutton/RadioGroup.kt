package com.aur3liux.encuestaprofe.view.components.radiobutton


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun RadioGroup(
    modifier: Modifier,
    sColor: Color,
    uColor: Color,
    sizeF : TextUnit = 19.sp,
    sizeFS : TextUnit = 21.sp,
    w: Dp = 250.dp,
    vPadding: Dp = 3.dp,
    items: List<DataRadioButton>,
    selection: DataRadioButton,
    onItemClick: ((DataRadioButton) -> Unit)
) {
    Column(modifier = modifier
        .selectableGroup()
        .padding(vertical = vPadding)) {
        items.forEach { item ->
            Spacer(modifier = Modifier.height(vPadding))
            LabelledRadioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = item == selection,
                        onClick = {
                            onItemClick(item)
                        },
                        role = Role.RadioButton
                    ),
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