import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np
import glob
import os

def extract_params_from_filename(filename):
    # Extract WS and SS from filename
    basename = os.path.basename(filename)
    # remove .csv extension
    basename = basename[:-4]
    params = basename.split('-')[1:]
    WS = float(params[1])
    SS = int(params[3])
    return WS, SS

# Get list of all CSV files
directory = './HW A-Star P2'
files = glob.glob(os.path.join(directory, '*.csv'))

win_data = []
run_data = []

# Load each file and calculate average win/fail value
for file in files:
    WS, SS = extract_params_from_filename(file)
    win_df = pd.read_csv(file, skiprows=2)
    win_df['win/fail'] = win_df['win/fail'].map({True: 1, False: 0})
    avg_win_fail = round(win_df['win/fail'].mean(), 3)
    run_time = round(win_df['run time'].mean()/1000.0, 3)
    win_data.append([WS, SS, avg_win_fail])
    run_data.append([WS, SS, run_time])

# Create DataFrame
win_df = pd.DataFrame(win_data, columns=['WS', 'SS', 'avg_win_fail'])
run_time_df = pd.DataFrame(run_data, columns=['WS', 'SS', 'run_time'])

# Pivot DataFrame to create matrix
pivot_win_df = win_df.pivot(index='WS',columns='SS',values='avg_win_fail')
pivot_run_time_df = run_time_df.pivot(index='WS',columns='SS',values='run_time')

# Plot heatmap for average win/fail
plt.figure(figsize=(10, 8))
sns.heatmap(pivot_win_df, annot=True, fmt='g', cmap='viridis')
plt.title('Average Win/Fail percentage Heatmap')

# Set x and y labels
plt.xlabel('Search Steps (SS)')
plt.ylabel('Window Size (WS)')

plt.gca().invert_yaxis()

# Save heatmap
plt.savefig('p2-win-heatmap.png')

# Plot heatmap for average run time
plt.figure(figsize=(10, 8))
sns.heatmap(pivot_run_time_df, annot=True, fmt='g', cmap='viridis')
plt.title('Average Run Time Heatmap in seconds')

# Set x and y labels
plt.xlabel('Search Steps (SS)')
plt.ylabel('Window Size (WS)')

plt.gca().invert_yaxis()

# Save heatmap
plt.savefig('p2-run-time-heatmap.png')
