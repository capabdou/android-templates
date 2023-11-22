package co.nimblehq.sample.compose.ui.screens.main.second

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

@Composable
fun SecondScreen(
    viewModel: SecondViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
    id: String,
) {
    SecondScreenContent(id)
}

@Composable
private fun SecondScreenContent(id: String) {
    Scaffold(topBar = {
        AppBar(R.string.second_title_bar)
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = stringResource(R.string.second_id_title, id),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SecondScreenPreview() {
    ComposeTheme {
        SecondScreenContent("1")
    }
}
