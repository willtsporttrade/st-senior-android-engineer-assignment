# st-senior-android-engineer-assignment

Assignment for Senior Android Engineer position at https://boards.greenhouse.io/sporttrade/jobs/4731608004?t=7e3c715f4us

## Setup

1. Install [AndroidStudio](https://developer.android.com/studio)
1. Fork this repo
1. Clone your fork with its submodules
1. Open the project in AndroidStudio

## Guidelines

1. You **must** use the included repository to interface with the data layer with view models
1. You **must** use [RxKotlin/RxJava](https://github.com/ReactiveX/RxKotlin) to observe changes in the data layer
1. You **cannot** remove dependencies
1. You can add dependencies, but you must provide justification in the pull request notes

## Assignment - Required

1. Finish implementing `PositionListFragment`
    1. Display the following for each represented `Position` entity
        1. `name`
        1. `price`, formatted as currency (US dollars)
    1. On tap of represented `Position`, push an instance of `PositionDetailsFragment` (see comment in `onCreateView()` method of the `PositionListFragment`)
1. Finish implementing `PositionDetailsFragment`
    1. Display the following for the represented `Position` entity
        1. `name`
        1. `criteriaName`
        1. `storyName`
        1. `price`, formatted as currency (US dollars)
        1. `quantity`, formatted as decimal with 4 significant digits
    1. Subscribe to the rxFlowable returned by `PositionRepository - observePosition(identifier: String)` and update the displayed values
1. Document your changes for `internal` or higher access level, ignore `override`s
1. Place all user visible strings in `strings.xml`

### Assignment - Optional

1. **Optional**: Use data bindings to connect the `PositionDetailsViewModel` data to the layout XML in the corresponding fragment
1. **Optional**: Add unit tests to (com.getsporttrade.assignment - test)
1. **Optional**: Add UI tests to (com.getsporttrade.assignment - androidTest)

## Assignment - Submission

1. Open a pull request against this repo using your fork