//
//  ThirdViewController.m
//  SFTHClient
//
//  Created by Tony Tam on 11/16/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import "ThirdViewController.h"
#import "SWGEnvironmentApi.h"

@interface ThirdViewController ()

@end

@implementation ThirdViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    connected = false;
    [self refreshState];
}


- (void)makeHappy {
    UIImage* image = [UIImage imageNamed:@"logo_swagger"];
    [self.logoButton setImage:image forState:UIControlStateNormal];
    [self.logoButton setNeedsLayout];
    
    connected = true;
}

- (void)makeSad {
    UIImage* image = [UIImage imageNamed:@"logo_sad_swagger"];
    [self.logoButton setImage:image forState:UIControlStateNormal];
    [self.logoButton setNeedsLayout];
    
    connected = false;
}

- (void)refreshState {
    /*
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    [api getAnalogInputsWithCompletionBlock:^(NSArray *output, NSError *error) {
        if(output) {
            [self makeHappy];
            for(SWGAnalogIO * io in output) {
                if([[io position] isEqualToNumber:@15]) {
                    self.label1.text = [NSString stringWithFormat:@"%.1lfC", [[io value] doubleValue ]];
                }
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    SWGEnvironmentApi* ev = [[SWGEnvironmentApi alloc] init];
    [ev getForecastWithCompletionBlock:@5 completionHandler:^(NSArray *output, NSError *error) {
        if(output) {
            [self makeHappy];
            int count = 0;
            for(SWGTemperatureForecast * f in output) {
                SWGTemperature * temp  = f.temp;
                NSNumber * daytimeTemp = [temp day];
                if(count == 0) {
                    self.label2.text = [NSString stringWithFormat:@"%.2lfF", [daytimeTemp doubleValue]];
                }
                else if(count == 1) {
                    self.label3.text = [NSString stringWithFormat:@"%.2lfF", [daytimeTemp doubleValue]];
                }
                else if(count == 2) {
                    self.label4.text = [NSString stringWithFormat:@"%.2lfF", [daytimeTemp doubleValue]];
                }
                else if(count == 3) {
                    self.label5.text = [NSString stringWithFormat:@"%.2lfF", [daytimeTemp doubleValue]];
                }
                else if(count == 4) {
                    self.label6.text = [NSString stringWithFormat:@"%.2lfF", [daytimeTemp doubleValue]];
                }
                count += 1;
            }
        }
        else {
            [self makeSad];
        }
    }];
     */
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
