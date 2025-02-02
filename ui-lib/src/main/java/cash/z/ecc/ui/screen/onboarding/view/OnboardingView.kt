package cash.z.ecc.ui.screen.onboarding.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cash.z.ecc.ui.R
import cash.z.ecc.ui.screen.common.Body
import cash.z.ecc.ui.screen.common.Header
import cash.z.ecc.ui.screen.common.NavigationButton
import cash.z.ecc.ui.screen.common.PinkProgress
import cash.z.ecc.ui.screen.common.PrimaryButton
import cash.z.ecc.ui.screen.common.SecondaryButton
import cash.z.ecc.ui.screen.common.TertiaryButton
import cash.z.ecc.ui.screen.onboarding.model.OnboardingStage
import cash.z.ecc.ui.screen.onboarding.model.Progress
import cash.z.ecc.ui.screen.onboarding.state.OnboardingState
import cash.z.ecc.ui.theme.MINIMAL_WEIGHT
import cash.z.ecc.ui.theme.ZcashTheme

@Preview
@Composable
fun ComposablePreview() {
    ZcashTheme(darkTheme = true) {
        Onboarding(
            OnboardingState(OnboardingStage.UnifiedAddresses),
            onImportWallet = {},
            onCreateWallet = {}
        )
    }
}

/**
 * @param onImportWallet Callback when the user decides to import an existing wallet.
 * @param onCreateWallet Callback when the user decides to create a new wallet.
 */
@Composable
fun Onboarding(
    onboardingState: OnboardingState,
    onImportWallet: () -> Unit,
    onCreateWallet: () -> Unit
) {
    Surface {
        Column {
            TopNavButtons(onboardingState)

            val onboardingStage = onboardingState.current.collectAsState().value

            when (onboardingStage) {
                OnboardingStage.ShieldedByDefault -> ShieldedByDefault()
                OnboardingStage.UnifiedAddresses -> UnifiedAddresses()
                OnboardingStage.More -> More()
                OnboardingStage.Wallet -> Wallet(
                    onCreateWallet = onCreateWallet,
                    onImportWallet = onImportWallet
                )
            }

            BottomNav(onboardingStage.getProgress(), onboardingState::goNext)
        }
    }
}

@Composable
private fun TopNavButtons(onboardingState: OnboardingState) {
    Row {
        if (onboardingState.hasPrevious()) {
            NavigationButton(onboardingState::goPrevious, stringResource(R.string.onboarding_back))
        }

        Spacer(
            Modifier
                .fillMaxWidth()
                .weight(MINIMAL_WEIGHT, true)
        )

        if (onboardingState.hasNext()) {
            NavigationButton(onboardingState::goToEnd, stringResource(R.string.onboarding_skip))
        }
    }
}

@Composable
private fun BottomNav(progress: Progress, onNext: () -> Unit) {
    if (progress.current != progress.last) {
        Column {
            SecondaryButton(onNext, stringResource(R.string.onboarding_next), Modifier.fillMaxWidth())

            // Converts from index to human numbering
            Body((progress.current.value + 1).toString())

            PinkProgress(progress, Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ShieldedByDefault() {
    Column {
        Content(
            image = ColorPainter(Color.Blue),
            imageContentDescription = stringResource(R.string.onboarding_1_image_content_description),
            headline = stringResource(R.string.onboarding_1_header),
            body = stringResource(R.string.onboarding_1_body)
        )
    }
}

@Composable
private fun UnifiedAddresses() {
    Column {
        Content(
            image = ColorPainter(Color.Blue),
            imageContentDescription = stringResource(R.string.onboarding_2_image_content_description),
            headline = stringResource(R.string.onboarding_2_header),
            body = stringResource(R.string.onboarding_2_body)
        )
    }
}

@Composable
private fun More() {
    Column {
        Content(
            image = ColorPainter(Color.Blue),
            imageContentDescription = stringResource(R.string.onboarding_3_image_content_description),
            headline = stringResource(R.string.onboarding_3_header),
            body = stringResource(R.string.onboarding_3_body)
        )
    }
}

@Composable
private fun Wallet(onCreateWallet: () -> Unit, onImportWallet: () -> Unit) {
    Column {
        PrimaryButton(onCreateWallet, stringResource(R.string.onboarding_4_create_new_wallet), Modifier.fillMaxWidth())
        TertiaryButton(
            onImportWallet, stringResource(R.string.onboarding_4_import_existing_wallet),
            Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Content(
    image: Painter,
    imageContentDescription: String?,
    headline: String,
    body: String
) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            // TODO [#17]: This suppression and magic number will get replaced once we have real assets
            @Suppress("MagicNumber")
            Image(image, imageContentDescription, Modifier.fillMaxSize(0.50f))
        }
        Header(headline)
        Body(body)
    }
}

@Composable
fun Callout(imageVector: ImageVector, contentDescription: String) {
    Box(modifier = Modifier.background(ZcashTheme.colors.callout)) {
        Icon(imageVector, contentDescription, tint = ZcashTheme.colors.onCallout)
    }
}
